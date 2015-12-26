package com.madlibs.websocketcontroller;

import com.google.gson.JsonObject;
import org.eclipse.jetty.websocket.api.Session;

/**
 * Controller to handle session join messages.
 * Created by Ran on 12/25/2015.
 */
public class SessionJoinController {

    private JsonObject parsedMessage;
    private Session session;

    /**
     * Constructs a controller to handle the request.
     * @param parsedMessage JsonObject passed in message.
     * @param session Websocket session.
     */
    public SessionJoinController(JsonObject parsedMessage, Session session) {
        this.parsedMessage = parsedMessage;
        this.session = session;
    }

    /**
     * Handles the request.
     */
    public void handle() {
        // TODO
    }

    // TODO
}
