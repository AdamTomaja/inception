package com.cydercode.inception.configuration;

import com.cydercode.inception.controller.action.WorldAction;
import com.cydercode.inception.database.NodeRepository;
import com.cydercode.inception.game.Game;
import com.cydercode.inception.model.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Configuration
public class GameConfiguration {

    @Autowired
    private WorldAction worldAction;

    @Autowired
    private NodeRepository nodeRepository;

    @Bean
    public Game game() throws Exception {
        Node node = new Node();
        nodeRepository.add(node);
        List<Node> nodes = nodeRepository.getAll();
        Optional<Node> game = recreateGame(nodes);
        if (game.isPresent()) {
            return (Game) game.get();
        }

        return new Game();
    }

    private Optional<Node> recreateGame(List<Node> nodes) {
        nodes.forEach(node -> {
            node.setChildren(node.getChildren().stream().map(n -> findNode(n.getId(), nodes)).collect(Collectors.toList()));
        });

        return findGame(nodes);
    }

    private Optional<Node> findGame(List<Node> nodes) {
        return nodes.stream().filter(n -> n instanceof Game).findFirst();
    }

    private Node findNode(String id, List<Node> nodes) {
        return nodes.stream().filter(n -> id.equals(n.getId())).findFirst().get();
    }
}
