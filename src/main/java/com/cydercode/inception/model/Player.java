package com.cydercode.inception.model;

import com.cydercode.inception.database.NodeEntity;
import com.cydercode.inception.events.server.ConsoleEvent;
import com.google.common.base.MoreObjects;

import java.util.Map;

import static java.lang.String.format;

public class Player extends Matter implements Named {

    private String nickname;

    public Player() {
    }

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

    public void receiveMessage(Player sender, String mode, String message) {
        fireEvent(new ConsoleEvent(format("[%s][%s]: %s", sender.getName(), mode, message)));
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
    public NodeEntity toNodeEntity() {
        NodeEntity nodeEntity = super.toNodeEntity();
        nodeEntity.setName(nickname);
        return nodeEntity;
    }

    @Override
    protected void restore(NodeEntity nodeEntity) {
        super.restore(nodeEntity);
        nickname = nodeEntity.getName();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("nickname", nickname)
                .toString();
    }
}
