package com.cydercode.inception.model;

import com.cydercode.inception.events.Event;
import com.cydercode.inception.events.EventListener;
import com.google.common.base.MoreObjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Map<String, Object> getPresentation() {
        return new HashMap<>();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("presentation", getPresentation())
                .toString();
    }
}
