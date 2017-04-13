package com.cydercode.inception.model;

import com.google.common.base.MoreObjects;

import java.util.UUID;

public class World extends Matter implements Named, Colored, Unique {

    private Color color = Color.random();
    private String id = UUID.randomUUID().toString();
    private String name;

    public World(Location location, String name) {
        super(location);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("color", color)
                .toString();
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
