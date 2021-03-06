package com.madlibs.websocketcontroller;

import com.google.gson.JsonObject;
import com.madlibs.config.AnonymousIdentifiers;
import com.madlibs.data.DatabaseService;
import com.madlibs.model.MadLibsSession;
import com.madlibs.server.MadLibsServer;
import com.madlibs.websocketcontroller.messages.*;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.util.List;

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
    public void handle() throws IOException {
        String sessionId = parsedMessage.get("id").getAsString();
        // Get session
        MadLibsSession gameSession = MadLibsServer.getInstance().getSessionById(sessionId);

        if (gameSession != null) {
            List<String> identifiers = gameSession.getParticipantIdentifiers();
            String identifier;
            if (parsedMessage.get("user") != null) {
                identifier = parsedMessage.get("user").getAsString();
                if (identifiers.contains(identifier)) {
                    session.getRemote().sendString(new JoinResponseFailureMessage(sessionId, "Another user with that identifier is already in the session!").getContent());
                    return;
                }
            } else {
                // Assign random celebrity name
                do {
                    identifier = AnonymousIdentifiers.getInstance().getRandomIdentifier();
                } while(identifiers.contains(identifier));
            }

            // Send all participants notification of user joining.
            gameSession.sendMessageToAllParticipants(new UserJoinedGameMessage(identifier, sessionId).getContent());
            // Add user to session
            MadLibsServer.getInstance().addParticipantToSession(sessionId, session, identifier);

            // Send success response, game state.
            session.getRemote().sendString(new JoinResponseSuccessMessage(sessionId, identifier).getContent());

            if (gameSession.isFinished()) {
                session.getRemote().sendString(new SessionCompleteMessage(DatabaseService.getInstance().getScript(gameSession.getId())).getContent());
            } else {
                gameSession.sendMessageToAllParticipants(new GameStateUpdateMessage(gameSession).getContent());
            }


        } else {
            session.getRemote().sendString(new JoinResponseFailureMessage(sessionId, "Session doesn't exist or has been removed").getContent());
        }
    }
}
