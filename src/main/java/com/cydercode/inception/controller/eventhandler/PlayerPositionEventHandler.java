package com.cydercode.inception.controller.eventhandler;

import com.cydercode.inception.controller.EventingWebSocketSession;
import com.cydercode.inception.controller.SessionsCache;
import com.cydercode.inception.events.client.PlayerPositionEvent;
import com.cydercode.inception.game.Game;
import com.cydercode.inception.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@EventType(PlayerPositionEvent.class)
public class PlayerPositionEventHandler implements EventHandler<PlayerPositionEvent> {

    @Autowired
    private Game game;

    @Autowired
    private SessionsCache sessionsCache;

    @Override
    public void handleEvent(PlayerPositionEvent event, EventingWebSocketSession session) throws Exception {
        Player player = sessionsCache.getMandatoryPlayer(session.getWebSocketSession());
        game.playerMoved(player, event.getLocation());
    }
}
