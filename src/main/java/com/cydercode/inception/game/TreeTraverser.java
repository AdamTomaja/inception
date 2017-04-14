package com.cydercode.inception.game;

import com.cydercode.inception.model.Named;
import com.cydercode.inception.model.Node;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Consumer;

@Component
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

    public Optional<Node> findWithName(String name, Node tree) {
        for (Node node : tree.getChildren()) {
            if (node instanceof Named && ((Named) node).getName().equals(name)) {
                return Optional.of(node);
            }
        }

        return Optional.empty();
    }
}
