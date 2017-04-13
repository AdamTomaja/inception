package com.cydercode.inception.events.server;


import com.cydercode.inception.events.Event;

public class NodeRemovedEvent extends Event {

    private String node;

    public NodeRemovedEvent(String node) {
        super("nodeRemovedEvent");
        this.node = node;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }
}
