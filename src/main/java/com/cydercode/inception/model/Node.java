package com.cydercode.inception.model;

import com.cydercode.inception.database.NodeEntity;
import com.cydercode.inception.events.Event;
import com.cydercode.inception.events.EventListener;
import com.google.common.base.MoreObjects;

import java.util.*;
import java.util.stream.Collectors;

public class Node implements Unique {

    protected String id = UUID.randomUUID().toString();

    private List<Node> children = new ArrayList<>();
    private List<EventListener> listeners = new ArrayList<>();

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public void fireEvent(Event event) {
        listeners.forEach(l -> l.onEvent(event));
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

    public Node withId(String id) {
        this.id = id;
        return this;
    }

    public NodeEntity toNodeEntity() {
        NodeEntity nodeEntity = new NodeEntity();
        nodeEntity.setId(id);
        nodeEntity.setType(this.getClass().getName());
        nodeEntity.setChildren(children.stream()
                .map(c -> c.getId()).collect(Collectors.toList()));
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
        children = nodeEntity.getChildren().stream().map(id -> new Node().withId(id)).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("presentation", getPresentation())
                .toString();
    }

    public void addListener(EventListener eventListener) {
        listeners.add(eventListener);
    }
}
