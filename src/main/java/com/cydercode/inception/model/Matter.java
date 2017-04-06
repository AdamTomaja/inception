package com.cydercode.inception.model;

public class Matter extends Node {

    private Location location;

    public Matter(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
