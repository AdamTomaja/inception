package com.cydercode.inception.game;


import com.cydercode.inception.io.NodePrinter;
import com.cydercode.inception.model.Location;
import com.cydercode.inception.model.Node;
import com.cydercode.inception.model.Player;
import com.cydercode.inception.model.World;

public class Game {

    private TreeTraverser treeTraverser = new TreeTraverser();
    private NodePrinter nodePrinter = new NodePrinter();
    private Node universe = new Node();

    public Player createNewPlayer(String name) {
        Player player = new Player(Location.random(), name);
        universe.getChildren().add(player);
        return player;
    }

    public World createWorld(Player player) {
        World world = new World(player.getLocation(), player.getNickname() + "-World");
        Node playerParent = treeTraverser.findParent(player, universe).get();
        playerParent.getChildren().add(world);
        return world;
    }

    public void teleport(Player player, World world) {
        Node oldParent = treeTraverser.findParent(player, universe).get();
        oldParent.getChildren().remove(player);
        world.getChildren().add(player);
    }

    public String getGameStringRepresentation() {
        return nodePrinter.print(universe);
    }
}
