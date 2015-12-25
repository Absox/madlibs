package com.madlibs.websocketcontroller;

import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import spark.Session;

/**
 * Websocket controller.
 * Created by Ran on 12/23/2015.
 */
@WebSocket
public class WebsocketController {

    @OnWebSocketConnect
    public void onConnect(Session session) {

    }

    @OnWebSocketMessage
    public void onMessage(Session session) {

    }

    @OnWebSocketClose
    public void onClose(Session session) {

    }

}
