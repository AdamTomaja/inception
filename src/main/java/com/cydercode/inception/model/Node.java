package com.cydercode.inception.model;

import com.cydercode.inception.events.Event;
import com.cydercode.inception.events.EventListener;

import java.util.ArrayList;
import java.util.List;

public class Node {

    private List<Node> children = new ArrayList<>();

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public void fireEvent(Event event) {
        for (Node child : children) {
            if (child instanceof EventListener) {
                ((EventListener) child).onEvent(event);
            }
        }
    }
}
