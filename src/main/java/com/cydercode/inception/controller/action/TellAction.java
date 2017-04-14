package com.cydercode.inception.controller.action;

import com.cydercode.inception.controller.Command;
import com.cydercode.inception.game.Game;
import com.cydercode.inception.game.TreeTraverser;
import com.cydercode.inception.model.Node;
import com.cydercode.inception.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.lang.String.join;

@Component
@ActionName("tell")
public class TellAction implements PlayerAction {

    @Autowired
    private TreeTraverser treeTraverser;

    @Override
    public void execute(Player player, Game game, Command command) {
        Node parentWorld = treeTraverser.findParent(player, game).get();
        parentWorld.getChildren().forEach(c -> {
            if (c instanceof Player) {
                ((Player) c).receiveMessage(player, "tell", join(" ", command.getParameters()));
            }
        });
    }
}
