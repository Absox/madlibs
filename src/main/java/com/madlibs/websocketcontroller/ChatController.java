package com.madlibs.websocketcontroller;

import com.google.gson.JsonObject;
import com.madlibs.model.ChatMessage;
import com.madlibs.model.MadLibsSession;
import com.madlibs.model.MadLibsSessionParticipant;
import com.madlibs.server.MadLibsServer;
import com.madlibs.websocketcontroller.messages.ChatReceivedMessage;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.util.Date;

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
    public void handle() throws IOException {
        MadLibsSession gameSession = MadLibsServer.getInstance().getSessionBySession(session);
        MadLibsSessionParticipant participant = gameSession.getParticipantBySession(session);

        // TODO error checking

        ChatMessage message = new ChatMessage(new Date().getTime(), participant.getIdentifier(), parsedMessage.get("message").getAsString());
        gameSession.addChatMessage(message);
        gameSession.sendMessageToAllParticipants(new ChatReceivedMessage(gameSession.getId(), message).getContent());

    }
}
