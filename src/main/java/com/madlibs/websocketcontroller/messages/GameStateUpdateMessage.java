package com.madlibs.websocketcontroller.messages;

import com.google.gson.Gson;
import com.madlibs.model.MadLibsSession;

/**
 * Game state update message.
 * Created by Ran on 12/29/2015.
 */
public class GameStateUpdateMessage extends WebsocketMessage {

    /**
     * Creates a game state update message.
     * @param gameSession GameSession whose state we're putting into JSON
     */
    public GameStateUpdateMessage(MadLibsSession gameSession) {
        content.addProperty("type", "gameStateUpdate");
        content.addProperty("id", gameSession.getId());
        content.add("users", new Gson().toJsonTree(gameSession.getParticipantIdentifiers()));
        content.addProperty("currentPrompt", gameSession.getCurrentPrompt());
        content.addProperty("currentTurn", gameSession.getCurrentParticipant().getIdentifier());
        content.addProperty("nextTurn", gameSession.getNextParticipant().getIdentifier());
    }
}
