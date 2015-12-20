package com.madlibs;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;

/**
 * Created by Ran on 12/20/2015.
 */
@WebSocket
public class SocketController {

    @OnWebSocketConnect
    public void connected(Session session) {
        System.out.println("Connected: " + session.getRemoteAddress());
    }

    @OnWebSocketClose
    public void close(Session session, int code, String reason) {
        System.out.println("Disconnected: " + session.getRemoteAddress());
    }

    @OnWebSocketMessage
    public void message(Session session, String message) {
        System.out.println("Received message from " + session.getRemoteAddress());
        System.out.println(message);
        try {
            session.getRemote().sendString("Received!");
        } catch (IOException e) {

        }
    }

}
