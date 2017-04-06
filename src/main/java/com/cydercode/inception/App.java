package com.cydercode.inception;

import com.cydercode.inception.events.EventListener;
import com.cydercode.inception.game.Game;
import com.cydercode.inception.model.Player;
import com.cydercode.inception.model.World;

public class App {

    public static void main(String[] args) {
        Game game = new Game();
        EventListener eventListener = new EventListener() {
            @Override
            public void onEvent(Object event) {
                System.out.println("Game event: " + event);
            }

            @Override
            public String toString() {
                return "EventListener";
            }
        };

        game.getChildren().add(eventListener);
        Player adam = game.createNewPlayer("Adam");
        adam.getChildren().add(eventListener);

        World adamsWorld = game.createWorld(adam);
        game.teleport(adam, adamsWorld);

        Player ann = game.createNewPlayer("Ann");
        ann.getChildren().add(eventListener);
        game.teleport(ann, adamsWorld);

        Player mark = game.createNewPlayer("Mark");
        mark.getChildren().add(eventListener);

        World thirdWorld = game.createWorld(adam);
        game.teleport(mark, thirdWorld);

        game.shout(adam, "Hello World!");
        game.tell(adam, "Hello Ann!!");

        System.out.println(game.getGameStringRepresentation());
    }
}
