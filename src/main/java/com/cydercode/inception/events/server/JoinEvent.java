package com.cydercode.inception.events.server;


import com.cydercode.inception.events.Event;

import java.util.Map;

public class JoinEvent extends Event {

    private Map<String, Object> player;

    public JoinEvent(Map<String, Object> player) {
        this.player = player;
    }

    public Map<String, Object> getPlayer() {
        return player;
    }

    public void setPlayer(Map<String, Object> player) {
        this.player = player;
    }
}
