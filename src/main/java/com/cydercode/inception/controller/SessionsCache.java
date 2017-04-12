package com.cydercode.inception.controller;

import com.cydercode.inception.model.Player;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;

@Component
public class SessionsCache {

    private Map<WebSocketSession, Player> sessionPlayerMap = new HashMap<>();

    public Player getPlayer(WebSocketSession session) {
        return sessionPlayerMap.get(session);
    }

    public boolean hasSession(WebSocketSession session) {
        return sessionPlayerMap.containsKey(session);
    }

    public void removeSession(WebSocketSession session) {
        sessionPlayerMap.remove(session);
    }

    public void addPlayer(WebSocketSession session, Player player) {
        sessionPlayerMap.put(session, player);
    }

    public Player getMandatoryPlayer(WebSocketSession session) {
        if (!sessionPlayerMap.containsKey(session)) {
            throw new RuntimeException("This session has not player assigned!");
        }

        return sessionPlayerMap.get(session);
    }
}
