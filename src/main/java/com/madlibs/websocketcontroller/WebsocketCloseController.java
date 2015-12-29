package com.madlibs.websocketcontroller;

import com.madlibs.model.MadLibsSession;
import com.madlibs.server.MadLibsServer;
import org.eclipse.jetty.websocket.api.Session;

/**
 * A controller to handle a websocket close event.
 * Created by Ran on 12/25/2015.
 */
public class WebsocketCloseController {

    private Session session;
    private int statusCode;
    private String reason;

    /**
     * Constructs a controller to handle the event.
     * @param session Websocket session.
     * @param statusCode statusCode of close event.
     * @param reason Reason for connection close.
     */
    public WebsocketCloseController(Session session, int statusCode, String reason) {
        this.session = session;
        this.statusCode = statusCode;
        this.reason = reason;
    }

    public void handle() {
        MadLibsSession gameSession = MadLibsServer.getInstance().getSessionBySession(this.session);
        if (gameSession != null) {
            MadLibsServer.getInstance().disconnectParticipant(this.session);
        }
    }

}
