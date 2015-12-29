package com.madlibs.websocketcontroller;

import com.google.gson.JsonObject;
import com.madlibs.model.MadLibsSession;
import com.madlibs.server.MadLibsServer;
import org.eclipse.jetty.websocket.api.Session;

/**
 * Websocket controller to handle chat messages.
 * Created by Ran on 12/25/2015.
 */
public class ChatController {

    private Session session;
    private JsonObject parsedMessage;

    /**
     * Constructs a controller to handle incoming chat messages.
     * @param session Websocket session.
     * @param parsedMessage Message received
     */
    public ChatController(JsonObject parsedMessage, Session session) {
        this.session = session;
        this.parsedMessage = parsedMessage;
    }

    /**
     * Handles the request.
     */
    public void handle() {
        MadLibsSession gameSession = MadLibsServer.getInstance().getSessionBySession(session);

        if (gameSession != null) {
            // TODO
        } else {
            // Issue failure message - not bound to session.

        }
    }
}
