package com.cydercode.inception;

import com.cydercode.inception.io.NodePrinter;
import com.cydercode.inception.model.Location;
import com.cydercode.inception.model.Node;
import com.cydercode.inception.model.Player;
import com.cydercode.inception.model.World;

public class App {

    public static void main(String[] args) {
        Node universe = new Node();
        Player player = new Player(new Location(2, 2, 2), "Adam");
        universe.getChildren().add(player);

        World earth = new World(new Location(10, 10, 10), "Earth");
        universe.getChildren().add(earth);

        World neptun = new World(new Location(20, 20, 20), "Neptun");
        earth.getChildren().add(neptun);

        System.out.println(new NodePrinter().print(universe));
    }
}
