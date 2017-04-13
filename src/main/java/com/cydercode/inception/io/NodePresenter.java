package com.cydercode.inception.io;

import com.cydercode.inception.model.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class NodePresenter {

    public Map<String, Object> nodeToMap(Node node) {
        Map<String, Object> childRepresentation = new HashMap<>();
        childRepresentation.put("type", node.getClass().getSimpleName());

        if (node instanceof Matter) {
            childRepresentation.put("location", ((Matter) node).getLocation());
        }

        if (node instanceof Named) {
            childRepresentation.put("name", ((Named) node).getName());
        }

        if (node instanceof Colored) {
            childRepresentation.put("color", ((Colored) node).getColor());
        }

        if (node instanceof Unique) {
            childRepresentation.put("id", ((Unique) node).getId());
        }

        return childRepresentation;
    }

}
