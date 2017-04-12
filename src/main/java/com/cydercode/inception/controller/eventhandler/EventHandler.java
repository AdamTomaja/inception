package com.cydercode.inception.controller.eventhandler;

import com.cydercode.inception.controller.EventingWebSocketSession;
import com.cydercode.inception.events.Event;

public interface EventHandler<T extends Event> {

    void handleEvent(T event, EventingWebSocketSession session) throws Exception;

}
