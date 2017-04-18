package com.cydercode.inception.events;

public class Event {

    protected String type;

    public Event() {
        type = this.getClass().getSimpleName();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
