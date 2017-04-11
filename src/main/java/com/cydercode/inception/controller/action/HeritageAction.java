package com.cydercode.inception.controller.action;

import com.cydercode.inception.controller.Command;
import com.cydercode.inception.events.ConsoleEvent;
import com.cydercode.inception.game.Game;
import com.cydercode.inception.model.Heritage;
import com.cydercode.inception.model.Player;
import org.springframework.stereotype.Component;

@Component
@ActionName("heritage")
public class HeritageAction implements PlayerAction {

    @Override
    public void execute(Player player, Game game, Command command) {
        Heritage heritage = game.createHeritage(player, String.join(" ", command.getParameters()));
        player.fireEvent(new ConsoleEvent("Created heritage at " + heritage.getLocation()));
    }
}
