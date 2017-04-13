package com.cydercode.inception.model;

import com.google.common.base.MoreObjects;

public class World extends Matter implements Named {

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
                .toString();
    }

}
