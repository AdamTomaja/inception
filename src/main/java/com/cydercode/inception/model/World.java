package com.cydercode.inception.model;

import com.google.common.base.MoreObjects;

public class World extends Matter {

    private String name;

    public World(Location location, String name) {
        super(location);
        this.name = name;
    }

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
                .toString();
    }
}
