package com.cydercode.inception.model;

import java.util.Map;
import java.util.UUID;

public class Matter extends Node implements Unique {

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
    public String getId() {
        return id;
    }

    @Override
    public Map<String, Object> getPresentation() {
        Map<String, Object> presentation = super.getPresentation();
        presentation.put("id", id);
        presentation.put("color", color);
        presentation.put("location", location);
        return presentation;
    }
}
