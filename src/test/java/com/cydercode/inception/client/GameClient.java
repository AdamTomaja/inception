package com.cydercode.inception.client;

import com.cydercode.inception.events.Event;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@ClientEndpoint
public class GameClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameClient.class);
    private List<Event> receivedEvents = new ArrayList<>();
    private final WebSocketContainer container;

    public GameClient() {
        container = ContainerProvider.getWebSocketContainer();
    }

    public void connect(String url) throws URISyntaxException, IOException, DeploymentException {
        container.connectToServer(this, new URI(url));
    }

    @OnOpen
    public void onOpen() {
        LOGGER.info("Connection open");
    }

    @OnMessage
    public void onMessage(String message) {
        LOGGER.info("Message received: {}", message);
        Event event = new Gson().fromJson(message, Event.class);
        LOGGER.info("Event parsed: {}", event);
        receivedEvents.add(event);
    }
}
