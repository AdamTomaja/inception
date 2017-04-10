package com.cydercode.inception.configuration;

import com.cydercode.inception.game.Game;
import com.cydercode.inception.model.Player;
import com.cydercode.inception.model.World;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GameConfiguration {

    @Bean
    public Game game() {
        Game game = new Game();
        Player adam = game.createNewPlayer("Adam");
        World earth = game.createWorld(adam, "adamsworld");
        earth.setName("Earth");
        game.teleport(adam, earth);
        return game;
    }
}
