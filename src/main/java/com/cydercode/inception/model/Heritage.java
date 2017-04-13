package com.cydercode.inception.model;

import com.google.common.base.MoreObjects;

import java.util.Map;

public class Heritage extends Matter {

    private final String content;
    private final String name;

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
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("content", content)
                .toString();
    }
}
