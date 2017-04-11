package com.cydercode.inception.controller.action;

import com.cydercode.inception.controller.Command;
import com.cydercode.inception.events.ConsoleEvent;
import com.cydercode.inception.game.Game;
import com.cydercode.inception.model.Player;
import com.cydercode.inception.model.World;
import org.springframework.stereotype.Component;

@Component
@ActionName("world")
public class WorldAction implements PlayerAction {
    @Override
    public void execute(Player player, Game game, Command command) {
        World world = game.createWorld(player, command.getParameters().get(0));
        player.fireEvent(new ConsoleEvent(world + " world created at " + world.getLocation()));
    }
}
