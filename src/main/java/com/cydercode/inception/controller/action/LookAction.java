package com.cydercode.inception.controller.action;

import com.cydercode.inception.controller.Command;
import com.cydercode.inception.events.server.ConsoleEvent;
import com.cydercode.inception.game.Game;
import com.cydercode.inception.game.TreeTraverser;
import com.cydercode.inception.model.Node;
import com.cydercode.inception.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ActionName("look")
public class LookAction implements PlayerAction {

    @Autowired
    private TreeTraverser treeTraverser;

    @Override
    public void execute(Player player, Game game, Command command) {
        Node parentWorld = treeTraverser.findParent(player, game).get();

        List<Node> result = new ArrayList<>(parentWorld.getChildren());
        result.remove(player);
        StringBuilder stringBuilder = new StringBuilder();
        for (Node node : result) {
            stringBuilder.append(node.toString());
            stringBuilder.append(", ");
        }
        player.fireEvent(new ConsoleEvent("Items near you: " + stringBuilder.toString()));
    }
}
