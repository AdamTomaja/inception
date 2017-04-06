package com.cydercode.inception.controller;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommandParser {

    public Command parse(String commandLine) {
        List<String> parameters = new ArrayList<>();
        String[] splitted = commandLine.split(" ");
        for (int i = 1; i < splitted.length; i++) {
            parameters.add(splitted[i]);
        }
        return new Command(splitted[0], parameters);
    }
}
