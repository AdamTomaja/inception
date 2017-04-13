package com.cydercode.inception.model;

import com.cydercode.inception.events.server.ConsoleEvent;
import com.google.common.base.MoreObjects;

import java.util.UUID;

public class Player extends Matter implements Named, Colored, Unique {

    private String id = UUID.randomUUID().toString();
    private Color color = Color.random();
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
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("nickname", nickname)
                .add("color", color)
                .toString();
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public String getId() {
        return id;
    }
}
