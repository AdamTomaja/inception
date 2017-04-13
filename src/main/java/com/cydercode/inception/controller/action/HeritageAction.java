package com.cydercode.inception.controller.action;

import com.cydercode.inception.controller.Command;
import com.cydercode.inception.events.server.ConsoleEvent;
import com.cydercode.inception.game.Game;
import com.cydercode.inception.model.Heritage;
import com.cydercode.inception.model.Player;
import org.springframework.stereotype.Component;

import static java.lang.String.join;

@Component
@ActionName("heritage")
public class HeritageAction implements PlayerAction {

    @Override
    public void execute(Player player, Game game, Command command) {
        Heritage heritage = game.createHeritage(player, command.getParameters().get(0), join(" ", command.getParameters()));
        player.fireEvent(new ConsoleEvent("Created heritage at " + heritage.getLocation()));
    }
}
