package com.cydercode.inception.controller;

import com.cydercode.inception.game.Game;
import com.cydercode.inception.model.Player;

import static java.lang.String.join;

public class CommandExecutor {

    private Game game;

    public CommandExecutor(Game game) {
        this.game = game;
    }

    public void execute(Player player, Command command) {
        switch (command.getAction()) {
            case "shout":
                game.shout(player, join(" ", command.getParameters()));
                break;
        }
    }
}
