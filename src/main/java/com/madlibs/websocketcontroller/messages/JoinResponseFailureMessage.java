package com.madlibs.websocketcontroller.messages;

/**
 * Message upon join session failure.
 * Created by Ran on 12/29/2015.
 */
public class JoinResponseFailureMessage extends WebsocketMessage {

    public JoinResponseFailureMessage(String sessionId, String reason) {
        content.addProperty("type", "gameJoinResponse");
        content.addProperty("status", "failure");
        content.addProperty("id", sessionId);
        content.addProperty("why", reason);
    }
}
