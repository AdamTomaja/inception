package com.cydercode.inception.model;

import com.google.common.base.MoreObjects;

public class Player extends Matter {

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
        fireEvent(nickname + " received message: " + message);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("nickname", nickname)
                .toString();
    }
}
