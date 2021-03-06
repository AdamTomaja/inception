package com.cydercode.inception.controller.action;

import com.cydercode.inception.controller.Command;
import com.cydercode.inception.game.Game;
import com.cydercode.inception.model.Player;

public interface PlayerAction {

    void execute(Player player, Game game, Command command);

}
