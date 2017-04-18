package com.cydercode.inception.database;

import com.cydercode.inception.model.Color;
import com.cydercode.inception.model.Location;
import com.google.gson.Gson;
import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

import java.util.List;

@Entity
public class NodeEntity {

    @PrimaryKey
    private String id;
    private String name;
    private String type;
    private String location;
    private String color;
    private String content;
    private List<String> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setLocation(Location location) {
        this.location = new Gson().toJson(location);
    }

    public Location getLocation() {
        return new Gson().fromJson(location, Location.class);
    }

    public void setColor(Color color) {
        this.color = new Gson().toJson(color);
    }

    public Color getColor() {
        return new Gson().fromJson(color, Color.class);
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public List<String> getChildren() {
        return children;
    }

    public void setChildren(List<String> children) {
        this.children = children;
    }
}
