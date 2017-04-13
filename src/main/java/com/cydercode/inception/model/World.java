package com.cydercode.inception.model;

import com.google.common.base.MoreObjects;

public class World extends Matter implements Named, Colored {

    private String name;
    private Color color;

    public World(Location location, String name) {
        super(location);
        this.name = name;
        color = Color.random();
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
}
