package com.cydercode.inception.controller.eventhandler;

import com.cydercode.inception.controller.*;
import com.cydercode.inception.events.client.CommandEvent;
import com.cydercode.inception.events.server.ConsoleEvent;
import com.cydercode.inception.game.Game;
import com.cydercode.inception.model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
@EventType(CommandEvent.class)
public class CommandEventHandler implements EventHandler<CommandEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandEventHandler.class);

    @Autowired
    private Game game;

    @Autowired
    private SessionsCache sessionsCache;

    @Autowired
    private CommandParser commandParser;

    @Autowired
    private CommandExecutor commandExecutor;

    @Override
    public void handleEvent(CommandEvent event, EventingWebSocketSession session) throws Exception {
        Command command = commandParser.parse(event.getCommand());
        LOGGER.info("Parsed command: {}", command);
        switch (command.getAction()) {
            case "help":
                session.sendEvent(new ConsoleEvent("join <nickname>, shout <message>"));
                break;

            default:
                commandExecutor.execute(getMandatoryPlayer(session.getWebSocketSession()), command);
                break;
        }
    }


    private Player getMandatoryPlayer(WebSocketSession session) {
        if (!sessionsCache.hasSession(session)) {
            throw new RuntimeException("Session was not associated with player! You must join the game.");
        }


        return sessionsCache.getPlayer(session);
    }
}
