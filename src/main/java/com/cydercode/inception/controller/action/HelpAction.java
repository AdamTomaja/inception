package com.cydercode.inception.controller.action;

import com.cydercode.inception.controller.Command;
import com.cydercode.inception.events.server.ConsoleEvent;
import com.cydercode.inception.game.Game;
import com.cydercode.inception.model.Player;
import com.google.common.collect.ImmutableMap;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ActionName("help")
public class HelpAction implements PlayerAction {

    private Map<String, String> descriptions = ImmutableMap.<String, String>builder()
            .put("up", "Teleport to the parent of current world")
            .put("heritage <name> <content>", "Create a heritage with given name and content")
            .put("location", "Display current position of player")
            .put("look", "Display existing nodes in current world")
            .put("name", "Display or set new name")
            .put("shout", "Say something to all players on the server")
            .put("tell", "Say samething to players on current world")
            .put("tp", "Teleport to position or other node")
            .put("world", "Display current world name or create new")
            .build();

    @Override
    public void execute(Player player, Game game, Command command) {
        String message = "Cannot find help for this command";

        if (command.getParameters().isEmpty()) {
            message = "Available commands: up, heritage, location, look, name, shout, tell, tp, world. Type help <command> to see more";
        } else {
            String action = command.getParameters().get(0);
            if(descriptions.containsKey(action)) {
                message = descriptions.get(action);
            }
        }

        player.fireEvent(new ConsoleEvent(message));
    }
}
