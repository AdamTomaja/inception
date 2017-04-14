package com.cydercode.inception.controller.action;

import com.cydercode.inception.controller.Command;
import com.cydercode.inception.events.server.ConsoleEvent;
import com.cydercode.inception.events.server.NodeCreatedEvent;
import com.cydercode.inception.game.Game;
import com.cydercode.inception.game.TreeTraverser;
import com.cydercode.inception.model.Node;
import com.cydercode.inception.model.Player;
import com.cydercode.inception.model.World;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ActionName("world")
public class WorldAction implements PlayerAction {

    @Autowired
    private TreeTraverser treeTraverser;

    @Override
    public void execute(Player player, Game game, Command command) {
        World world = createWorld(player, game, command.getParameters().get(0));
        player.fireEvent(new ConsoleEvent(world + " world created at " + world.getLocation()));
    }

    public World createWorld(Player player, Game game, String worldName) {
        World world = new World(player.getLocation(), worldName);
        Node playerParent = treeTraverser.findParent(player, game).get();
        playerParent.getChildren().add(world);
        game.sendToNeighbors(world, new NodeCreatedEvent(world.getPresentation()));
        return world;
    }
}
