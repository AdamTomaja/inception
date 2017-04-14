package com.cydercode.inception.controller.action;

import com.cydercode.inception.controller.Command;
import com.cydercode.inception.game.Game;
import com.cydercode.inception.game.TreeTraverser;
import com.cydercode.inception.model.Node;
import com.cydercode.inception.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@ActionName("up")
public class GoUpAction implements PlayerAction {

    @Autowired
    private TeleportAction teleportAction;

    @Autowired
    private TreeTraverser treeTraverser;

    @Override
    public void execute(Player player, Game game, Command command) {
        Optional<Node> target = treeTraverser.findParent(treeTraverser.findParent(player, game).get(), game);
        if(!target.isPresent()) {
            throw new RuntimeException("Cannot find place to teleport!");
        }

        teleportAction.teleport(game, player, target.get());
    }
}
