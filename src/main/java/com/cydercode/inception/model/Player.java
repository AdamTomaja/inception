package com.cydercode.inception.model;

import com.cydercode.inception.events.server.ConsoleEvent;
import com.google.common.base.MoreObjects;

import java.util.Map;

public class Player extends Matter implements Named {

    private String nickname;

    public Player(Location location, String nickname) {
        super(location);
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void receiveMessage(String message) {
        fireEvent(new ConsoleEvent(nickname + " received message: " + message));
    }

    @Override
    public String getName() {
        return nickname;
    }

    @Override
    public Map<String, Object> getPresentation() {
        Map<String, Object> presentation = super.getPresentation();
        presentation.put("name", nickname);
        return presentation;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("nickname", nickname)
                .toString();
    }
}
