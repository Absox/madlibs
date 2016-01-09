package com.madlibs.websocketcontroller;

import com.google.gson.JsonObject;
import com.madlibs.model.MadLibsSession;
import com.madlibs.server.MadLibsServer;
import com.madlibs.websocketcontroller.messages.GameStateUpdateMessage;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;

/**
 * Controller for game state requests.
 * Created by Ran on 1/8/2016.
 */
public class GameStateRequestController {

    JsonObject parsedRequest;
    Session session;

    /**
     * Constructs a controller to handle the request.
     * @param parsedRequest Request.
     * @param session Session.
     */
    public GameStateRequestController(JsonObject parsedRequest, Session session) {
        this.parsedRequest = parsedRequest;
        this.session = session;
    }

    public void handle() throws IOException {

        String sessionId = parsedRequest.get("id").getAsString();
        MadLibsSession gameSession = MadLibsServer.getInstance().getSessionById(sessionId);
        if (gameSession != null) {
            session.getRemote().sendString(new GameStateUpdateMessage(gameSession).getContent());
        } else {
            JsonObject failure = new JsonObject();
            failure.addProperty("type", "gameStateUpdateFailure");
            failure.addProperty("why", "Session does not exist!");
            session.getRemote().sendString(failure.toString());
        }


    }
}
