package com.cydercode.inception.model;

import com.cydercode.inception.database.NodeEntity;
import com.cydercode.inception.events.Event;
import com.cydercode.inception.events.EventListener;
import com.google.common.base.MoreObjects;

import java.util.*;

public class Node implements Unique {

    protected String id = UUID.randomUUID().toString();

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
        HashMap<String, Object> presentation = new HashMap<>();
        presentation.put("type", this.getClass().getSimpleName());
        presentation.put("id", id);
        return presentation;
    }

    @Override
    public String getId() {
        return id;
    }

    public NodeEntity toNodeEntity() {
        NodeEntity nodeEntity = new NodeEntity();
        nodeEntity.setId(id);
        nodeEntity.setType(this.getClass().getName());
        return nodeEntity;
    }

    public static Node fromNodeEntity(NodeEntity nodeEntity) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class<Node> clazz = (Class<Node>) Class.forName(nodeEntity.getType());
        Node instance = clazz.newInstance();
        instance.restore(nodeEntity);
        return instance;
    }

    protected void restore(NodeEntity nodeEntity) {
        id = nodeEntity.getId();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("presentation", getPresentation())
                .toString();
    }
}
