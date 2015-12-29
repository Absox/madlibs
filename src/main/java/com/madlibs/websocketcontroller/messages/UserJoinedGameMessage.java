package com.madlibs.websocketcontroller.messages;

import com.google.gson.JsonObject;

/**
 * Message passed through websocket when user joins a game.
 * Created by Ran on 12/29/2015.
 */
public class UserJoinedGameMessage extends WebsocketMessage {
    /**
     * Constructs a method object.
     * @param identifier Identifier of the user who has joined
     * @param sessionId Id of the session they joined
     */
    public UserJoinedGameMessage(String identifier, String sessionId) {

        content = new JsonObject();
        content.addProperty("type", "userJoinedUpdate");
        content.addProperty("id", sessionId);
        content.addProperty("user", identifier);

    }

}
