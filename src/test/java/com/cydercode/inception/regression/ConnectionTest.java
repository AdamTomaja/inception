package com.cydercode.inception.regression;

import com.cydercode.inception.client.GameClient;
import org.junit.Test;

import java.nio.channels.UnresolvedAddressException;

public class ConnectionTest {

    @Test
    public void shouldConnect() throws Exception {
        // given
        GameClient client = new GameClient();

        // when
        client.connect("ws://localhost:8080/player");
    }

    @Test(expected = UnresolvedAddressException.class)
    public void shouldNotConnect() throws Exception {
        // given
        GameClient client = new GameClient();

        // when
        client.connect("ws://localhosasdt:8080/player");
    }
}
