package com.cydercode.inception.model;

import com.cydercode.inception.database.NodeEntity;
import com.google.common.base.MoreObjects;

import java.util.Map;

public class Heritage extends Matter {

    private String content;
    private String name;

    public Heritage(String name, String content, Location location) {
        super(location);
        this.content = content;
        this.name = name;
    }

    @Override
    public Map<String, Object> getPresentation() {
        Map<String, Object> presentation = super.getPresentation();
        presentation.put("content", content);
        presentation.put("name", name);
        return presentation;
    }

    @Override
    public NodeEntity toNodeEntity() {
        NodeEntity nodeEntity = super.toNodeEntity();
        nodeEntity.setContent(content);
        nodeEntity.setName(name);
        return nodeEntity;
    }

    @Override
    protected void restore(NodeEntity nodeEntity) {
        super.restore(nodeEntity);
        content = nodeEntity.getContent();
        name = nodeEntity.getName();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("content", content)
                .toString();
    }
}
