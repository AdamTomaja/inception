package com.cydercode.inception.events.server;

import com.cydercode.inception.events.Event;
import com.cydercode.inception.model.Node;

import java.util.Map;

public class NodeCreatedEvent extends Event {

    private Map<String, Object> node;

    public NodeCreatedEvent(Node node) {
        this.node = node.getPresentation();
    }

    public Map<String, Object> getNode() {
        return node;
    }

    public void setNode(Map<String, Object> node) {
        this.node = node;
    }
}
