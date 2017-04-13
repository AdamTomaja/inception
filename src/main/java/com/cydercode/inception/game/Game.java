package com.cydercode.inception.game;


import com.cydercode.inception.events.Event;
import com.cydercode.inception.events.server.*;
import com.cydercode.inception.io.NodePrinter;
import com.cydercode.inception.model.*;
import com.google.common.base.MoreObjects;

import java.util.*;

public class Game extends Node {

    private TreeTraverser treeTraverser = new TreeTraverser();
    private NodePrinter nodePrinter = new NodePrinter();

    public Player createNewPlayer(String name) {
        Player player = new Player(Location.random(), name);
        getChildren().add(player);
        sendToNeighbors(player, new NodeCreatedEvent(nodeToMap(player)));
        return player;
    }

    public World createWorld(Player player, String worldname) {
        World world = new World(player.getLocation(), worldname);
        Node playerParent = treeTraverser.findParent(player, this).get();
        playerParent.getChildren().add(world);
        return world;
    }

    public RenderEvent createRenderFor(Player player) {
        Scene scene = new Scene();
        List<Object> children = new ArrayList<>();
        Node parent = treeTraverser.findParent(player, this).get();;

        for (Node child : parent.getChildren()) {
            if(child != player)
            children.add(nodeToMap(child));
        }

        scene.put("children", children);
        return new RenderEvent(scene);
    }

    public Map<String, Object> nodeToMap(Node node) {
        Map<String, Object> childRepresentation = new HashMap<>();
        childRepresentation.put("type", node.getClass().getSimpleName());

        if (node instanceof Matter) {
            childRepresentation.put("location", ((Matter) node).getLocation());
        }

        if (node instanceof Named) {
            childRepresentation.put("name", ((Named) node).getName());
        }
        return childRepresentation;
    }

    public Optional<Node> getNodeWithName(Player player, String nodeName) {
        return treeTraverser.findWithName(nodeName, treeTraverser.findParent(player, this).get());
    }

    public void teleport(Player player, Location location) {
        player.setLocation(location);
        sendToNeighbors(player, new NodePositionChangedEvent(player, location));
    }

    private void sendToNeighbors(Player player, Event event) {
        Node parent = treeTraverser.findParent(player, this).get();
        for (Node child : parent.getChildren()) {
            if (child != player) {
                child.fireEvent(event);
            }
        }
    }

    public void removePlayer(Player player) {
        sendToNeighbors(player, new NodeRemovedEvent(player.getName()));
        treeTraverser.findParent(player, this).get().getChildren().remove(player);
    }

    public World teleport(Player player, String nodename) {
        Node nodeParent = treeTraverser.findParent(player, this).get();
        Optional<Node> nodeWithName = treeTraverser.findWithName(nodename, nodeParent);
        if (!nodeWithName.isPresent()) {
            throw new RuntimeException("Cannot find node with name " + nodename);
        }

        Node world = nodeWithName.get();

        if (world instanceof World) {
            teleport(player, (World) world);
            return (World) world;
        } else {
            throw new RuntimeException("You can teleport only to world!");
        }
    }

    public void teleport(Player player, World world) {
        Node oldParent = treeTraverser.findParent(player, this).get();
        oldParent.getChildren().remove(player);
        world.getChildren().add(player);
    }

    public void shout(Player player, String message) {
        treeTraverser.executeForEach(this, n -> {
            if (n instanceof Player && n != player) {
                ((Player) n).receiveMessage(message);
            }
        });
    }

    public String getGameStringRepresentation() {
        return nodePrinter.print(this);
    }

    public void tell(Player player, String message) {
        Node parentWorld = treeTraverser.findParent(player, this).get();
        parentWorld.getChildren().forEach(c -> {
            if (c instanceof Player && c != player) {
                ((Player) c).receiveMessage(message);
            }
        });
    }

    public List<Node> lookAround(Player player) {
        Node parentWorld = treeTraverser.findParent(player, this).get();
        List<Node> result = new ArrayList<>(parentWorld.getChildren());
        result.remove(player);
        return result;
    }

    public Node getWorldOf(Player player) {
        return treeTraverser.findParent(player, this).get();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("Place", "Universe")
                .toString();
    }

    public Heritage createHeritage(Player player, String content) {
        Heritage heritage = new Heritage(content, player.getLocation());
        treeTraverser.findParent(player, this).get().getChildren().add(heritage);
        return heritage;
    }
}
