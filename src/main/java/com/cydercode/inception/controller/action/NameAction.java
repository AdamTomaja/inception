package com.cydercode.inception.controller.action;

import com.cydercode.inception.controller.Command;
import com.cydercode.inception.events.server.ConsoleEvent;
import com.cydercode.inception.game.Game;
import com.cydercode.inception.model.Player;
import org.springframework.stereotype.Component;

@Component
@ActionName("name")
public class NameAction implements PlayerAction {

    @Override
    public void execute(Player player, Game game, Command command) {
        if (!command.getParameters().isEmpty()) {
            game.setName(player, command.getParameters().get(0));
        }

        player.fireEvent(new ConsoleEvent("Your name is " + player.getNickname()));
    }
}
