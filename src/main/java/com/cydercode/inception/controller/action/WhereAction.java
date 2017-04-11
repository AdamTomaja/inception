package com.cydercode.inception.controller.action;

import com.cydercode.inception.controller.Command;
import com.cydercode.inception.events.ConsoleEvent;
import com.cydercode.inception.game.Game;
import com.cydercode.inception.model.Node;
import com.cydercode.inception.model.Player;
import org.springframework.stereotype.Component;

@Component
@ActionName("where")
public class WhereAction implements PlayerAction {
    @Override
    public void execute(Player player, Game game, Command command) {
        Node playerPlace = game.getWorldOf(player);
        player.fireEvent(new ConsoleEvent("You are on " + playerPlace + " world"));
    }
}
