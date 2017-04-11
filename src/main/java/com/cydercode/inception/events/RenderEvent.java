package com.cydercode.inception.events;

import com.cydercode.inception.model.Node;
import com.cydercode.inception.model.World;


public class RenderEvent extends Event {

    private Node world;

    public RenderEvent(Node world) {
        super("renderEvent");
        this.world = world;
    }

    public Node getWorld() {
        return world;
    }

    public void setWorld(Node world) {
        this.world = world;
    }
}
