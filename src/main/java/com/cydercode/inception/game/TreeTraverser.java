package com.cydercode.inception.game;

import com.cydercode.inception.model.Node;

import java.util.Optional;
import java.util.function.Consumer;

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

    public void executeForEach(Node tree, Consumer<Node> nodeConsumer) {
        nodeConsumer.accept(tree);
        tree.getChildren().forEach(child -> executeForEach(child, nodeConsumer));
    }
}
