package com.madlibs.websocketcontroller;

import org.eclipse.jetty.websocket.api.Session;

/**
 * Controller to handle incoming websocket messages.
 * Created by Ran on 12/25/2015.
 */
public class WebsocketMessageController {

    private Session session;
    private String message;

    /**
     * Constructs a controller to handle the message.
     * @param session Websocket session.
     * @param message Message contents.
     */
    public WebsocketMessageController(Session session, String message) {
        this.session = session;
        this.message = message;
    }


    public void handle() {
        // TODO
    }
}