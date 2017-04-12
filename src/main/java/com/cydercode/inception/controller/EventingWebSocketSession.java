package com.cydercode.inception.controller;

import com.cydercode.inception.events.Event;
import com.google.common.base.Objects;
import com.google.gson.Gson;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public class EventingWebSocketSession {

    private final WebSocketSession session;

    public EventingWebSocketSession(WebSocketSession session) {
        this.session = session;
    }

    public void sendEvent(Event event) throws IOException {
        session.sendMessage(new TextMessage(new Gson().toJson(event)));
    }

    public WebSocketSession getWebSocketSession() {
        return session;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventingWebSocketSession that = (EventingWebSocketSession) o;
        return Objects.equal(session, that.session);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(session);
    }
}
