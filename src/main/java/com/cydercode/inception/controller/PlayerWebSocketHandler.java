package com.cydercode.inception.controller;


import com.cydercode.inception.controller.eventhandler.EventHandler;
import com.cydercode.inception.controller.eventhandler.EventType;
import com.cydercode.inception.events.Event;
import com.cydercode.inception.events.EventListener;
import com.cydercode.inception.events.client.CommandEvent;
import com.cydercode.inception.events.server.ConsoleEvent;
import com.cydercode.inception.events.server.JoinEvent;
import com.cydercode.inception.game.Game;
import com.cydercode.inception.io.NodePresenter;
import com.cydercode.inception.model.Player;
import com.google.gson.Gson;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;
import java.util.Optional;

@Component
public class PlayerWebSocketHandler extends TextWebSocketHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerWebSocketHandler.class);

    @Autowired
    private List<EventHandler> eventHandlerList;

    @Autowired
    private Game game;

    @Autowired
    private SessionsCache sessionsCache;

    @Autowired
    private NodePresenter nodePresenter;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        EventingWebSocketSession eventingWebSocketSession = new EventingWebSocketSession(session);
        LOGGER.info("Connection estabilished: {}", session);
        Player player = game.createNewPlayer("anonymous" + RandomStringUtils.randomAlphabetic(5));
        sessionsCache.addPlayer(session, player);

        player.getChildren().add(new EventListener() {
            @Override
            public void onEvent(Event event) {
                try {
                    eventingWebSocketSession.sendEvent(event);
                } catch (Exception e) {
                    LOGGER.error("Error while sending to client", e);
                }
            }
        });

        player.receiveMessage("Hello " + player.getNickname());
        player.fireEvent(new JoinEvent(nodePresenter.nodeToMap(player)));
        player.fireEvent(game.createRenderFor(player));
        super.afterConnectionEstablished(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        EventingWebSocketSession eventingWebSocketSession = new EventingWebSocketSession(session);
        try {
            Event event = parseEvent(message.getPayload());
            LOGGER.info("Event received: {}", event);
            Optional<EventHandler> handler = getEventHandler(event);
            if (!handler.isPresent()) {
                throw new RuntimeException("Cannot find handler for event: " + event.getClass());
            }
            handler.get().handleEvent(event, eventingWebSocketSession);
        } catch (Exception e) {
            eventingWebSocketSession.sendEvent(new ConsoleEvent("Error: " + e.getMessage()));
            LOGGER.error("Error when handling message", e);
        }

        super.handleTextMessage(session, message);
    }

    private Optional<EventHandler> getEventHandler(Event event) {
        return eventHandlerList.stream()
                .filter(eventHandler ->
                        eventHandler.getClass().getAnnotation(EventType.class)
                                .value().equals(event.getClass())).findFirst();
    }

    private <T extends Event> T parseEvent(String eventPayload) throws ClassNotFoundException {
        Gson gson = new Gson();
        Event event = gson.fromJson(eventPayload, Event.class);
        Class eventType = Class.forName(CommandEvent.class.getPackage().getName() + "." + event.getType());
        event = gson.fromJson(eventPayload, (Class<Event>) eventType);
        return (T) event;
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        LOGGER.info("Connection closed: {} - {}", session, status);
        if (sessionsCache.hasSession(session)) {
            game.removePlayer(sessionsCache.getMandatoryPlayer(session));
            sessionsCache.removeSession(session);
        }

        super.afterConnectionClosed(session, status);
    }
}
