package com.madlibs.websocketcontroller;

import com.madlibs.model.MadLibsSession;
import com.madlibs.server.MadLibsServer;
import org.eclipse.jetty.websocket.api.Session;

/**
 * Websocket controller to handle chat messages.
 * Created by Ran on 12/25/2015.
 */
public class ChatController {

    private Session session;
    private String message;

    /**
     * Constructs a controller to handle it.
     * @param session Websocket session.
     * @param message Message received
     */
    public ChatController(Session session, String message) {
        this.session = session;
        this.message = message;
    }

    public void handle() {
        MadLibsSession gameSession = MadLibsServer.getInstance().getSessionBySession(session);

        if (gameSession == null) {
            // Issue failure message - not bound to session.
        }

        // TODO

    }
}
