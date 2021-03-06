package com.cydercode.inception.controller;

import com.cydercode.inception.controller.action.ActionName;
import com.cydercode.inception.controller.action.PlayerAction;
import com.cydercode.inception.controller.action.ShoutAction;
import com.cydercode.inception.game.Game;
import com.cydercode.inception.model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Component
public class CommandExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandExecutor.class);

    @Autowired
    private Game game;

    @Autowired
    private List<PlayerAction> playerActions;

    @Autowired
    private ShoutAction defaultAction;

    @PostConstruct
    public void init() {
        LOGGER.info("Loaded actions: {}", playerActions);
    }

    public void execute(Player player, Command command) {
        Optional<PlayerAction> action = searchForAction(command.getAction());
        if (action.isPresent()) {
            action.get().execute(player, game, command);
        } else {
            defaultAction.execute(player, game, pripareForDefaultAction(command));
        }
    }

    private Command pripareForDefaultAction(Command command) {
        command.getParameters().add(0, command.getAction());
        return command;
    }

    private Optional<PlayerAction> searchForAction(String actionName) {
        return playerActions.stream()
                .filter(a -> a.getClass().getAnnotation(ActionName.class).value().equals(actionName))
                .findFirst();
    }
}
