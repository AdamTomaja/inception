package com.cydercode.inception.model;

import com.google.common.base.MoreObjects;

public class Heritage extends Matter {

    private final String content;

    public Heritage(String content, Location location) {
        super(location);
        this.content = content;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("content", content)
                .toString();
    }
}
