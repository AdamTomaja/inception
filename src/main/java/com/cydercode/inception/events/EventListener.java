package com.cydercode.inception.events;

import com.cydercode.inception.model.Node;

public abstract class EventListener extends Node {
    public abstract void onEvent(Event event);
}
