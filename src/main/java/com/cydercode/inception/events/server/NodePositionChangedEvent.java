package com.cydercode.inception.events.server;

import com.cydercode.inception.events.Event;
import com.cydercode.inception.model.Location;
import com.cydercode.inception.model.Player;

public class NodePositionChangedEvent extends Event {

    private String node;
    private Location location;

    public NodePositionChangedEvent(Player player, Location location) {
        super("nodePositionChangedEvent");
        this.node = player.getName();
        this.location = location;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
