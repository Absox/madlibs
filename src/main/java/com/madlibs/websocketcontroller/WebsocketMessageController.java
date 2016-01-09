package com.madlibs.websocketcontroller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;

/**
 * Controller to handle incoming websocket messages.
 * Created by Ran on 12/25/2015.
 */
public class WebsocketMessageController {

    private Session session;
    private String message;

    private JsonObject parsedMessage;

    /**
     * Constructs a controller to handle the message.
     * @param session Websocket session.
     * @param message Message contents.
     */
    public WebsocketMessageController(Session session, String message) {
        this.session = session;
        this.message = message;
        this.parsedMessage = new JsonParser().parse(message).getAsJsonObject();
    }

    /**
     * Handles the message.
     */
    public void handle() throws IOException {
        switch (this.parsedMessage.get("type").getAsString()) {
            case "gameStateRequest":
                new GameStateRequestController(parsedMessage, session).handle();
                break;
            case "gameJoin":
                new SessionJoinController(parsedMessage, session).handle();
                break;
            case "responseSubmit":
                new ResponseSubmitController(parsedMessage, session).handle();
                break;
            case "chatSend":
                new ChatController(parsedMessage, session).handle();
                break;
        }
    }
}