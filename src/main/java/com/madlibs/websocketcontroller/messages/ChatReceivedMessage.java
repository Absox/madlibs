package com.madlibs.websocketcontroller.messages;

import com.madlibs.model.ChatMessage;

/**
 * Websocket message upon chat message receipt.
 * Created by Ran on 12/29/2015.
 */
public class ChatReceivedMessage extends WebsocketMessage {

    /**
     * Constructs a message indicating that a chat has been received.
     * @param sessionId Id of session.
     * @param message ChatMessage that was received.
     */
    public ChatReceivedMessage(String sessionId, ChatMessage message) {
        content.addProperty("type", "chatReceive");
        content.addProperty("id", sessionId);
        content.addProperty("user", message.getUser());
        content.addProperty("message", message.getValue());
        content.addProperty("time", message.getDateStamp());
    }

}
