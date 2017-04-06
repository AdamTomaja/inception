package com.cydercode.inception;

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
}
