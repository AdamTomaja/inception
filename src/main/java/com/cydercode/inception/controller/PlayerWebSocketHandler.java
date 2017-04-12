package com.cydercode.inception.controller;


import com.cydercode.inception.events.CommandEvent;
import com.cydercode.inception.events.ConsoleEvent;
import com.cydercode.inception.events.Event;
import com.cydercode.inception.events.EventListener;
import com.cydercode.inception.game.Game;
import com.cydercode.inception.model.Player;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class PlayerWebSocketHandler extends TextWebSocketHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerWebSocketHandler.class);

    private Map<WebSocketSession, Player> sessionPlayerMap = new HashMap<>();

    @Autowired
    private CommandParser commandParser;

    @Autowired
    private Game game;

    @Autowired
    private CommandExecutor commandExecutor;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        LOGGER.info("Connection estabilished: {}", session);
        sendEvent(session, new ConsoleEvent("Hi! Type 'help' to get available commands."));
        sendEvent(session, new ConsoleEvent("Type 'join <nickname>' to start the game"));
        super.afterConnectionEstablished(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            Event event = parseEvent(message.getPayload());
            LOGGER.info("Event received: {}", event);
            if (event instanceof CommandEvent) {
                executeCommandEvent((CommandEvent) event, session);
            }
        } catch (Exception e) {
            sendEvent(session, new ConsoleEvent("Error: " + e.getMessage()));
            LOGGER.error("Error when handling message", e);
        }

        super.handleTextMessage(session, message);
    }

    private void executeCommandEvent(CommandEvent event, WebSocketSession session) throws IOException {
        Command command = commandParser.parse(event.getCommand());
        LOGGER.info("Parsed command: {}", command);
        switch (command.getAction()) {
            case "join":
                Player player = game.createNewPlayer(command.getParameters().get(0));
                sessionPlayerMap.put(session, player);

                player.getChildren().add(new EventListener() {
                    @Override
                    public void onEvent(Object event) {
                        try {
                            sendEvent(session, event);
                        } catch (Exception e) {
                            LOGGER.error("Error while sending to client", e);
                        }
                    }
                });

                player.receiveMessage("Hello " + player.getNickname());
                player.fireEvent(game.createRenderFor(player));
                break;
            case "help":
                session.sendMessage(new TextMessage("join <nickname>, shout <message>"));
                break;

            default:
                commandExecutor.execute(getMandatoryPlayer(session), command);
                break;
        }
    }

    private <T extends Event> T parseEvent(String eventPayload) throws ClassNotFoundException {
        Gson gson = new Gson();
        Event event = gson.fromJson(eventPayload, Event.class);
        Class eventType = Class.forName(Event.class.getPackage().getName() + "." + event.getType());
        event = gson.fromJson(eventPayload, (Class<Event>) eventType);
        return (T) event;
    }

    private void sendEvent(WebSocketSession session, Object event) throws IOException {
        session.sendMessage(new TextMessage(new Gson().toJson(event)));
    }

    private Player getMandatoryPlayer(WebSocketSession session) {
        if (!sessionPlayerMap.containsKey(session)) {
            throw new RuntimeException("Session was not associated with player! You must join the game.");
        }


        return sessionPlayerMap.get(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        LOGGER.info("Connection closed: {} - {}", session, status);
        sessionPlayerMap.remove(session);
        super.afterConnectionClosed(session, status);
    }
}
