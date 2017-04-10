package com.cydercode.inception.controller.action;

import com.cydercode.inception.controller.Command;
import com.cydercode.inception.game.Game;
import com.cydercode.inception.model.Matter;
import com.cydercode.inception.model.Node;
import com.cydercode.inception.model.Player;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@ActionName("distance")
public class DistanceAction implements PlayerAction {

    @Override
    public void execute(Player player, Game game, Command command) {
        String nodeName = command.getParameters().get(0);
        Optional<Node> node = game.getNodeWithName(player, nodeName);
        if (!node.isPresent()) {
            throw new RuntimeException("Cannot find node with name: " + nodeName);
        }

        if (!(node.get() instanceof Matter)) {
            throw new RuntimeException("Cannot calculate distance from " + node.get());
        }

        player.fireEvent("Distance: " + player.getLocation().distanceFrom(((Matter) node.get()).getLocation()));
    }
}
