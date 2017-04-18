package com.cydercode.inception.client;

import org.hamcrest.Matcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.jayway.awaitility.Awaitility.await;
import static org.assertj.core.api.Assertions.assertThat;

@ClientEndpoint
public class GameClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameClient.class);
    private List<String> receivedMessages = new ArrayList<>();
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
    public void onMessage(String message) throws ClassNotFoundException {
        try {
            LOGGER.info("Message received: {}", message);
            receivedMessages.add(message);
        } catch (Throwable e) {
            LOGGER.error("Cannot parse event from server", e);
        }
    }

    public void waitForMessage(Matcher<String> matcher, int count) {
        await().atMost(5, TimeUnit.SECONDS).until(() -> {
            getMatching(matcher, count);
        });
    }

    public List<String> getMatching(Matcher<String> matcher, int count) {
        List<String> collected = receivedMessages.stream()
                .filter(m -> matcher.matches(m))
                .collect(Collectors.toList());

        assertThat(collected).hasSize(count);

        return collected;
    }

}
