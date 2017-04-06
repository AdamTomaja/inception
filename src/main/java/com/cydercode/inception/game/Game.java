package com.cydercode.inception.game;


import com.cydercode.inception.io.NodePrinter;
import com.cydercode.inception.model.Location;
import com.cydercode.inception.model.Node;
import com.cydercode.inception.model.Player;
import com.cydercode.inception.model.World;

public class Game extends Node {

    private TreeTraverser treeTraverser = new TreeTraverser();
    private NodePrinter nodePrinter = new NodePrinter();

    public Player createNewPlayer(String name) {
        Player player = new Player(Location.random(), name);
        getChildren().add(player);
        fireEvent("Player created: " + name);
        return player;
    }

    public World createWorld(Player player) {
        World world = new World(player.getLocation(), player.getNickname() + "-World");
        Node playerParent = treeTraverser.findParent(player, this).get();
        playerParent.getChildren().add(world);
        return world;
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
}
