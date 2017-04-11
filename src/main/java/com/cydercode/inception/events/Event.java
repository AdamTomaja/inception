package com.cydercode.inception.events;

/**
 * Created by mint on 11.04.17.
 */
public class Event {

    private String type;

    public Event(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
