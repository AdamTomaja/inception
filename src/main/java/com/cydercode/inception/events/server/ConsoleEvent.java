package com.cydercode.inception.events.server;

import com.cydercode.inception.events.Event;

public class ConsoleEvent extends Event {

    private String content;

    public ConsoleEvent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEventType() {
        return this.getClass().getSimpleName();
    }
}
