package com.cydercode.inception.events.server;


import com.cydercode.inception.events.Event;
import com.cydercode.inception.model.Unique;

public class NodeRemovedEvent extends Event {

    private String node;

    public NodeRemovedEvent(Unique node) {
        super("nodeRemovedEvent");
        this.node = node.getId();
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }
}
