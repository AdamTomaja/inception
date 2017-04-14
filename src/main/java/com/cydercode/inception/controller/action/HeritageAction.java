package com.cydercode.inception.controller.action;

import com.cydercode.inception.controller.Command;
import com.cydercode.inception.events.server.ConsoleEvent;
import com.cydercode.inception.events.server.NodeCreatedEvent;
import com.cydercode.inception.game.Game;
import com.cydercode.inception.game.TreeTraverser;
import com.cydercode.inception.model.Heritage;
import com.cydercode.inception.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.lang.String.join;

@Component
@ActionName("heritage")
public class HeritageAction implements PlayerAction {

    @Autowired
    private TreeTraverser treeTraverser;

    @Override
    public void execute(Player player, Game game, Command command) {
        Heritage heritage = new Heritage(command.getParameters().get(0), join(" ", command.getParameters()), player.getLocation());
        treeTraverser.findParent(player, game).get().getChildren().add(heritage);
        game.sendToNeighbors(heritage, new NodeCreatedEvent(heritage));

        player.fireEvent(new ConsoleEvent("Created heritage at " + heritage.getLocation()));
    }
}
