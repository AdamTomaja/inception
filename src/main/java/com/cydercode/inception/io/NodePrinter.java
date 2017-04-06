package com.cydercode.inception.io;

import com.cydercode.inception.model.Matter;
import com.cydercode.inception.model.Node;

import static java.lang.System.lineSeparator;

public class NodePrinter {

    public String print(Node node) {
        StringBuilder stringBuilder = new StringBuilder();
        print(node, stringBuilder, 0);
        return stringBuilder.toString();
    }

    private void print(Node node, StringBuilder stringBuilder, int level) {
        appendTabs(stringBuilder, level);
        stringBuilder.append(node.toString());
        if (node instanceof Matter) {
            stringBuilder.append(' ' + ((Matter) node).getLocation().toString());
        }
        stringBuilder.append(lineSeparator());

        for (Node child : node.getChildren()) {
            print(child, stringBuilder, level + 1);
        }
    }

    private void appendTabs(StringBuilder stringBuilder, int level) {
        for (int i = 0; i < level; i++) {
            stringBuilder.append('\t');
        }
    }
}
