package com.cydercode.inception.events.server;

import com.cydercode.inception.events.Event;
import com.cydercode.inception.model.Location;
import com.cydercode.inception.model.Player;

public class PlayerPositionChangedEvent extends Event {

    private String player;
    private Location location;

    public PlayerPositionChangedEvent(Player player, Location location) {
        super("playerPositionChangedEvent");
        this.player = player.getName();
        this.location = location;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
