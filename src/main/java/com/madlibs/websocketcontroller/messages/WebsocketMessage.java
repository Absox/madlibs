package com.madlibs.websocketcontroller.messages;

import com.google.gson.JsonObject;

/**
 * Base message class.
 * Created by Ran on 12/29/2015.
 */
public abstract class WebsocketMessage {

    protected JsonObject content;

    /**
     * Base constructor for a websocket message.
     */
    protected WebsocketMessage() {
        this.content = new JsonObject();
    }

    /**
     * Accessor for content of message.
     * @return String containing json message.
     */
    public String getContent() {
        return this.content.toString();
    }

}
