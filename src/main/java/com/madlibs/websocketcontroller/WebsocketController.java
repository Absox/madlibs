package com.madlibs.websocketcontroller;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;

/**
 * Main websocket controller.
 * Created by Ran on 12/23/2015.
 */
@WebSocket
public class WebsocketController {

    @OnWebSocketConnect
    public void onConnect(Session session) {
        System.out.println("Session " + session.getLocalAddress() + " connected");
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        System.out.println("Message received: " + message);
        System.out.println("Source: " + session.getRemoteAddress());
        session.getRemote().sendString("Message received!");
    }

    @OnWebSocketClose
    public void onClose(Session session, int statusCode, String reason) {
        System.out.println("Session " + session.getLocalAddress() + " disconnected with status " + statusCode + " reason " + reason);
    }

}
