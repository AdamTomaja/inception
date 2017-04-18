package com.cydercode.inception.events.server;

import com.cydercode.inception.events.Event;

public class RenderEvent extends Event {

    private Scene scene;

    public RenderEvent(Scene scene) {
        this.scene = scene;
    }

    public Scene getScene() {
        return scene;
    }

    public void setWorld(Scene scene) {
        this.scene = scene;
    }
}
