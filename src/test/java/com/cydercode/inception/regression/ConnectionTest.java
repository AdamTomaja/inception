package com.cydercode.inception.regression;

import com.cydercode.inception.client.GameClient;
import org.junit.Test;

import java.nio.channels.UnresolvedAddressException;

import static net.javacrumbs.jsonunit.JsonMatchers.jsonPartMatches;
import static org.hamcrest.Matchers.*;

public class ConnectionTest {

    @Test
    public void shouldConnect() throws Exception {
        // given
        GameClient client = new GameClient();

        // when
        client.connect("ws://localhost:8080/player");

        // then
        client.waitForMessage(allOf(
                jsonPartMatches("type", equalTo("ConsoleEvent")),
                jsonPartMatches("content", startsWith("Hello "))), 1);
        client.waitForMessage(jsonPartMatches("type", equalTo("JoinEvent")), 1);
        client.waitForMessage(jsonPartMatches("type", equalTo("RenderEvent")), 1);
    }

    @Test(expected = UnresolvedAddressException.class)
    public void shouldNotConnect() throws Exception {
        // given
        GameClient client = new GameClient();

        // when
        client.connect("ws://localhosasdt:8080/player");
    }
}
