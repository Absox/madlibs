package com.madlibs.websocketcontroller.messages;

import com.google.gson.JsonObject;

/**
 * Websocket message for when user leaves game.
 * Created by Ran on 12/29/2015.
 */
public class UserLeftGameMessage extends WebsocketMessage {

    /**
     * Constructs a method object.
     * @param identifier Identifier of the user who has joined
     * @param sessionId Id of the session they joined
     */
    public UserLeftGameMessage(String identifier, String sessionId) {
        content = new JsonObject();
        content.addProperty("type", "userLeftUpdate");
        content.addProperty("id", sessionId);
        content.addProperty("user", identifier);
    }
}
