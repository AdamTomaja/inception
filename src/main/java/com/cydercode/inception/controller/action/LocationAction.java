package com.cydercode.inception.controller.action;

import com.cydercode.inception.controller.Command;
import com.cydercode.inception.events.ConsoleEvent;
import com.cydercode.inception.game.Game;
import com.cydercode.inception.model.Player;
import org.springframework.stereotype.Component;

@Component
@ActionName("location")
public class LocationAction implements PlayerAction {

    @Override
    public void execute(Player player, Game game, Command command) {
        player.fireEvent(new ConsoleEvent("Your location: " + player.getLocation()));
    }
}
