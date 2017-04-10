package com.cydercode.inception.controller.action;

import com.cydercode.inception.controller.Command;
import com.cydercode.inception.game.Game;
import com.cydercode.inception.model.Player;
import org.springframework.stereotype.Component;

import static java.lang.String.join;

@Component
@ActionName("tell")
public class TellAction implements PlayerAction {
    @Override
    public void execute(Player player, Game game, Command command) {
        game.tell(player, join(" ", command.getParameters()));
    }
}
