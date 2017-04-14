package com.cydercode.inception.game;


import com.cydercode.inception.events.Event;
import com.cydercode.inception.events.server.*;
import com.cydercode.inception.model.Location;
import com.cydercode.inception.model.Node;
import com.cydercode.inception.model.Player;
import com.google.common.base.MoreObjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Game extends Node {

    private TreeTraverser treeTraverser = new TreeTraverser();

    public Player createNewPlayer(String name) {
        Player player = new Player(Location.random(), name);
        getChildren().add(player);
        sendToNeighbors(player, new NodeCreatedEvent(player.getPresentation()));
        sendToNeighbors(player, new ConsoleEvent(player.getName() + " joined to Your world!"));
        return player;
    }

    public RenderEvent createRenderFor(Player player) {
        Scene scene = new Scene();
        List<Object> children = new ArrayList<>();
        Node parent = treeTraverser.findParent(player, this).get();

        for (Node child : parent.getChildren()) {
            if (child != player) {
                children.add(child.getPresentation());
            }
        }

        scene.put("children", children);
        return new RenderEvent(scene);
    }

    public Optional<Node> getNodeWithName(Player player, String nodeName) {
        return treeTraverser.findWithName(nodeName, treeTraverser.findParent(player, this).get());
    }

    public void sendToAllOnTheWorld(Player player, Event event) {
        Node parent = treeTraverser.findParent(player, this).get();
        for (Node child : parent.getChildren()) {
            child.fireEvent(event);
        }
    }

    public void sendToNeighbors(Node player, Event event) {
        Node parent = treeTraverser.findParent(player, this).get();
        for (Node child : parent.getChildren()) {
            if (child != player) {
                child.fireEvent(event);
            }
        }
    }

    public void removePlayer(Player player) {
        sendToNeighbors(player, new NodeRemovedEvent(player));
        treeTraverser.findParent(player, this).get().getChildren().remove(player);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("Place", "Universe")
                .toString();
    }


    public void setName(Player player, String name) {
        sendToNeighbors(player, new ConsoleEvent(String.format("%s changed name to %s", player.getName(), name)));
        player.setNickname(name);
    }
}
