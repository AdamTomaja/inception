package com.cydercode.inception.game;

import com.cydercode.inception.model.Node;

import java.util.Optional;

public class TreeTraverser {

    public Optional<Node> findParent(Node node, Node tree) {
        if (tree.getChildren().contains(node)) {
            return Optional.of(tree);
        }

        for (Node child : tree.getChildren()) {
            Optional<Node> parent = findParent(node, child);
            if (parent.isPresent()) {
                return parent;
            }
        }

        return Optional.empty();
    }
}
