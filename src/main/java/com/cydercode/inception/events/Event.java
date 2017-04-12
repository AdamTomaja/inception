package com.cydercode.inception.events;

public class Event {

    protected String type;

    public Event() {
        //
    }

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
