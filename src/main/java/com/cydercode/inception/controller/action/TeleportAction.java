package com.cydercode.inception.controller.action;

import com.cydercode.inception.controller.Command;
import com.cydercode.inception.events.server.ConsoleEvent;
import com.cydercode.inception.events.server.NodeCreatedEvent;
import com.cydercode.inception.events.server.NodePositionChangedEvent;
import com.cydercode.inception.events.server.NodeRemovedEvent;
import com.cydercode.inception.game.Game;
import com.cydercode.inception.game.TreeTraverser;
import com.cydercode.inception.model.Location;
import com.cydercode.inception.model.Node;
import com.cydercode.inception.model.Player;
import com.cydercode.inception.model.World;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@ActionName("tp")
public class TeleportAction implements PlayerAction {

    @Autowired
    private TreeTraverser treeTraverser;

    @Override
    public void execute(Player player, Game game, Command command) {
        String targetParameter = command.getParameters().get(0);
        Optional<Location> locationOptional = Location.tryParse(targetParameter);
        Object place = null;

        if (locationOptional.isPresent()) {
            Location location = locationOptional.get();
            teleport(game, player, location);
            place = location;
        } else {
            World targetWorld = teleport(game, player, targetParameter);
            place = targetWorld;
        }

        player.fireEvent(new ConsoleEvent("Teleported You to " + place));
    }

    public void playerMoved(Game game, Player player, Location location) {
        player.setLocation(location);
        game.sendToNeighbors(player, new NodePositionChangedEvent(player, location));
    }

    public void teleport(Game game, Player player, Location location) {
        player.setLocation(location);
        game.sendToAllOnTheWorld(player, new NodePositionChangedEvent(player, location));
    }

    public World teleport(Game game, Player player, String nodename) {
        Node nodeParent = treeTraverser.findParent(player, game).get();
        Optional<Node> nodeWithName = treeTraverser.findWithName(nodename, nodeParent);
        if (!nodeWithName.isPresent()) {
            throw new RuntimeException("Cannot find node with name " + nodename);
        }

        Node world = nodeWithName.get();

        if (world instanceof World) {
            teleport(game, player, (World) world);
            return (World) world;
        } else {
            throw new RuntimeException("You can teleport only to world!");
        }
    }

    public void teleport(Game game, Player player, Node place) {
        Node oldParent = treeTraverser.findParent(player, game).get();
        game.sendToNeighbors(player, new NodeRemovedEvent(player));
        oldParent.getChildren().remove(player);
        place.getChildren().add(player);
        player.fireEvent(game.createRenderFor(player));
        game.sendToNeighbors(player, new NodeCreatedEvent(player));
        player.fireEvent(new ConsoleEvent("Teleported You to " + place));
    }
}
