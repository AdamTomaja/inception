package com.cydercode.inception.controller.action;

import com.cydercode.inception.controller.Command;
import com.cydercode.inception.game.Game;
import com.cydercode.inception.game.TreeTraverser;
import com.cydercode.inception.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.lang.String.join;

@Component
@ActionName("shout")
public class ShoutAction implements PlayerAction {

    @Autowired
    private TreeTraverser treeTraverser;

    @Override
    public void execute(Player player, Game game, Command command) {
        treeTraverser.executeForEach(game, n -> {
            if (n instanceof Player) {
                ((Player) n).receiveMessage(player, "shout", join(" ", command.getParameters()));
            }
        });
    }
}
