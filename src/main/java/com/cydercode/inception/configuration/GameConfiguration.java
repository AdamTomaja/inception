package com.cydercode.inception.configuration;

import com.cydercode.inception.controller.action.WorldAction;
import com.cydercode.inception.game.Game;
import com.cydercode.inception.model.Location;
import com.cydercode.inception.model.Player;
import com.cydercode.inception.model.World;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.stream.IntStream;

@Configuration
public class GameConfiguration {

    @Autowired
    private WorldAction worldAction;

    @Bean
    public Game game() {
        Game game = new Game();
        Player adam = game.createNewPlayer("Adam");
        IntStream.range(0, 100).forEach(i -> {
            World world = worldAction.createWorld(adam, game, "adamsworld");
            world.setLocation(Location.random());
            world.setName("Wrd_" + RandomStringUtils.randomAlphabetic(4));
        });

        return game;
    }
}
