package com.cydercode.inception;

import com.cydercode.inception.game.Game;
import com.cydercode.inception.model.Player;
import com.cydercode.inception.model.World;

public class App {

    public static void main(String[] args) {
        Game game = new Game();
        Player adam = game.createNewPlayer("Adam");
        Player mark = game.createNewPlayer("Mark");
        World adamsWorld = game.createWorld(adam);
        game.teleport(adam, adamsWorld);
        World thirdWorld = game.createWorld(adam);
        game.teleport(mark, thirdWorld);

        System.out.println(game.getGameStringRepresentation());
    }
}
