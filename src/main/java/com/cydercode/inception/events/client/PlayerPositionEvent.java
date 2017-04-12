package com.cydercode.inception.events.client;

import com.cydercode.inception.events.Event;
import com.cydercode.inception.model.Location;

public class PlayerPositionEvent extends Event {

    private Location location;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
