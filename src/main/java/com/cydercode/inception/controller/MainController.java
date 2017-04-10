package com.cydercode.inception.controller;

import com.cydercode.inception.game.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @Autowired
    private Game game;

    @RequestMapping("/")
    public String home() {
        return "index";
    }
}
