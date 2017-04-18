package com.cydercode.inception.model;

import com.cydercode.inception.database.NodeEntity;
import com.sleepycat.persist.model.Entity;

import java.util.Map;

@Entity
public class Matter extends Node {

    private Color color = Color.random();

    private Location location;

    public Matter() {
    }

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
    protected void restore(NodeEntity nodeEntity) {
        super.restore(nodeEntity);
        color = nodeEntity.getColor();
        location = nodeEntity.getLocation();
    }

    @Override
    public NodeEntity toNodeEntity() {
        NodeEntity nodeEntity = super.toNodeEntity();
        nodeEntity.setColor(color);
        nodeEntity.setLocation(location);
        return nodeEntity;
    }

    @Override
    public Map<String, Object> getPresentation() {
        Map<String, Object> presentation = super.getPresentation();
        presentation.put("color", color);
        presentation.put("location", location);
        return presentation;
    }
}
