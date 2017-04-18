package com.cydercode.inception.controller.action;

import com.cydercode.inception.controller.Command;
import com.cydercode.inception.events.server.ConsoleEvent;
import com.cydercode.inception.game.Game;
import com.cydercode.inception.game.TreeTraverser;
import com.cydercode.inception.model.Matter;
import com.cydercode.inception.model.Node;
import com.cydercode.inception.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Component
@ActionName("look")
public class LookAction implements PlayerAction {

    @Autowired
    private TreeTraverser treeTraverser;

    @Override
    public void execute(Player player, Game game, Command command) {
        Node parentWorld = treeTraverser.findParent(player, game).get();

        Stream<Node> nodeStream = parentWorld.getChildren().stream().filter(n -> n != player);

        if (!command.getParameters().isEmpty()) {
            double distance = Double.parseDouble(command.getParameters().get(0));
            nodeStream = nodeStream.filter(n -> n instanceof Matter)
                    .filter(n -> ((Matter) n).getLocation().distanceFrom(player.getLocation()) < distance);
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (Node node : nodeStream.collect(toList())) {
            stringBuilder.append(node.toString());
            stringBuilder.append(", ");
        }
        player.fireEvent(new ConsoleEvent("Items near you: " + stringBuilder.toString()));
    }
}
