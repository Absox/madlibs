package com.madlibs.websocketcontroller;

import com.google.gson.JsonObject;
import com.madlibs.model.MadLibsSession;
import com.madlibs.model.MadLibsSessionParticipant;
import com.madlibs.server.MadLibsServer;
import com.madlibs.websocketcontroller.messages.GameStateUpdateMessage;
import com.madlibs.websocketcontroller.messages.ResponseSubmissionFailureMessage;
import com.madlibs.websocketcontroller.messages.SessionCompleteMessage;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;

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
    public void handle() throws IOException {

        MadLibsSession gameSession = MadLibsServer.getInstance().getSessionBySession(session);
        MadLibsSessionParticipant participant = gameSession.getParticipantBySession(session);
        String responseValue = parsedMessage.get("value").getAsString();

        // It's our turn
        if (gameSession.getCurrentParticipant().equals(participant)) {
            gameSession.addResponse(responseValue);

            if (gameSession.isFinished()) {

                MadLibsServer.getInstance().finalizeSession(gameSession.getId());
                // TODO update spec on session complete message
                gameSession.sendMessageToAllParticipants(new SessionCompleteMessage(gameSession).getContent());

            } else {
                gameSession.sendMessageToAllParticipants(new GameStateUpdateMessage(gameSession).getContent());
            }

        } else {
            session.getRemote().sendString(new ResponseSubmissionFailureMessage("Not your turn!").getContent());
        }
    }
}
