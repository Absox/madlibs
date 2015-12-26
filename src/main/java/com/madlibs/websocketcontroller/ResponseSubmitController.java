package com.madlibs.websocketcontroller;

import com.google.gson.JsonObject;
import org.eclipse.jetty.websocket.api.Session;

/**
 * Controller to handle response submission messages.
 * Created by Ran on 12/25/2015.
 */
public class ResponseSubmitController {

    private JsonObject parsedMessage;
    private Session session;

    public ResponseSubmitController(JsonObject parsedMessage, Session session) {
        this.parsedMessage = parsedMessage;
        this.session = session;
    }

    /**
     * Handles request.
     */
    public void handle() {
        // TODO
    }
}
