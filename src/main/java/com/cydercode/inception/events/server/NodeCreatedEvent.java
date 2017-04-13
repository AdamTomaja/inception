package com.cydercode.inception.events.server;

import com.cydercode.inception.events.Event;

import java.util.Map;

public class NodeCreatedEvent extends Event {

    private Map<String, Object> node;

    public NodeCreatedEvent(Map<String, Object> node) {
        super("nodeCreatedEvent");
        this.node = node;
    }

    public Map<String, Object> getNode() {
        return node;
    }

    public void setNode(Map<String, Object> node) {
        this.node = node;
    }
}
