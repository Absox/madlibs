package com.madlibs.websocketcontroller;

import com.google.gson.JsonObject;
import com.madlibs.config.AnonymousIdentifiers;
import com.madlibs.model.MadLibsSession;
import com.madlibs.server.MadLibsServer;
import com.madlibs.websocketcontroller.messages.GameStateUpdateMessage;
import com.madlibs.websocketcontroller.messages.JoinResponseFailureMessage;
import com.madlibs.websocketcontroller.messages.JoinResponseSuccessMessage;
import com.madlibs.websocketcontroller.messages.UserJoinedGameMessage;
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
            String identifier;
            if (parsedMessage.get("userIdentifier") != null) {
                identifier = parsedMessage.get("userIdentifier").getAsString();
            } else {
                // Assign random celebrity name
                List<String> identifiers = gameSession.getParticipantIdentifiers();
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
            session.getRemote().sendString(new GameStateUpdateMessage(gameSession).getContent());

        } else {
            session.getRemote().sendString(new JoinResponseFailureMessage(sessionId, "Session doesn't exist or has been removed").getContent());
        }
    }
}
