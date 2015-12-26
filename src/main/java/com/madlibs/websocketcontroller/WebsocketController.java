package com.madlibs.websocketcontroller;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;

/**
 * Main websocket controller. Delegates calls toother controllers.
 * Created by Ran on 12/23/2015.
 */
@WebSocket
public class WebsocketController {

    @OnWebSocketConnect
    public void onConnect(Session session) {
        // Do nothing.
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        new WebsocketMessageController(session, message).handle();
    }

    @OnWebSocketClose
    public void onClose(Session session, int statusCode, String reason) {
        new WebsocketCloseController(session, statusCode, reason).handle();
    }

}
