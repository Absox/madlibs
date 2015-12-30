package com.madlibs.websocketcontroller.messages;

/**
 * Response on game join success.
 * Created by Ran on 12/29/2015.
 */
public class JoinResponseSuccessMessage extends WebsocketMessage {

    /**
     * Constructs message.
     * @param sessionId Id of game session.
     * @param identifier Identifier of user
     */
    public JoinResponseSuccessMessage(String sessionId, String identifier) {
        content.addProperty("type", "gameJoinResponse");
        content.addProperty("status", "success");
        content.addProperty("id", sessionId);
        content.addProperty("user", identifier);
    }
}
