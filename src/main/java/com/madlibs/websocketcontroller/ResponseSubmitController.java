package com.madlibs.websocketcontroller;

import com.google.gson.JsonObject;
import com.madlibs.model.MadLibsSession;
import com.madlibs.model.MadLibsSessionParticipant;
import com.madlibs.server.MadLibsServer;
import org.eclipse.jetty.websocket.api.Session;

/**
 * Controller to handle response submission messages.
 * Created by Ran on 12/25/2015.
 */
public class ResponseSubmitController {

    private JsonObject parsedMessage;
    private Session session;

    public ResponseSubmitController(JsonObject parsedMessage, Session session) {
        this.parsedMessage = parsedMessage;
        this.session = session;
    }

    /**
     * Handles request.
     */
    public void handle() {

        MadLibsSession gameSession = MadLibsServer.getInstance().getSessionBySession(session);
        MadLibsSessionParticipant participant = gameSession.getParticipantBySession(session);

        // It's our turn
        if (gameSession.getCurrentParticipant().equals(participant)) {

        } else {

        }

        // TODO
    }
}
