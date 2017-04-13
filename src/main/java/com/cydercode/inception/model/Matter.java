package com.cydercode.inception.model;

import java.util.UUID;

public class Matter extends Node implements Colored, Unique {

    private Color color = Color.random();
    private String id = UUID.randomUUID().toString();
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

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public String getId() {
        return id;
    }
}
