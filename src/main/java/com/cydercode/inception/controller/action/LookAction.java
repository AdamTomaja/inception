package com.cydercode.inception.controller.action;

import com.cydercode.inception.controller.Command;
import com.cydercode.inception.events.server.ConsoleEvent;
import com.cydercode.inception.game.Game;
import com.cydercode.inception.model.Node;
import com.cydercode.inception.model.Player;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ActionName("look")
public class LookAction implements PlayerAction {

    @Override
    public void execute(Player player, Game game, Command command) {
        List<Node> nodes = game.lookAround(player);
        StringBuilder stringBuilder = new StringBuilder();
        for (Node node : nodes) {
            stringBuilder.append(node.toString());
            stringBuilder.append(", ");
        }
        player.fireEvent(new ConsoleEvent("Items near you: " + stringBuilder.toString()));
    }
}
