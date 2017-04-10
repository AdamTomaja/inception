package com.cydercode.inception.controller.action;

import com.cydercode.inception.controller.Command;
import com.cydercode.inception.game.Game;
import com.cydercode.inception.model.Location;
import com.cydercode.inception.model.Player;
import com.cydercode.inception.model.World;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@ActionName("tp")
public class TeleportAction implements PlayerAction {

    @Override
    public void execute(Player player, Game game, Command command) {
        String targetParameter = command.getParameters().get(0);
        Optional<Location> locationOptional = Location.tryParse(targetParameter);
        Object place = null;

        if(locationOptional.isPresent()) {
            Location location = locationOptional.get();
            game.teleport(player, location);
            place = location;
        } else {
            World targetWorld = game.teleport(player, targetParameter);
            place = targetWorld;
        }

        player.fireEvent("Teleported You to " + place);
    }
}
