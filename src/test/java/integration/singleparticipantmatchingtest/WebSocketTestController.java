package integration.singleparticipantmatchingtest;

import com.madlibs.model.MadLibsSessionParticipant;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

/**
 * Created by Ran on 12/25/2015.
 */
@WebSocket
public class WebSocketTestController {

    private static MadLibsSessionParticipant participant;

    @OnWebSocketConnect
    public void onConnect(Session session) {
        System.out.println("Connected");
        participant = new MadLibsSessionParticipant("testParticipant", session);
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) {
        if (participant.getSession().equals(session)) {
            System.out.println(message);
        }
    }

    @OnWebSocketClose
    public void onClose(Session session, int status, String reason) {
        participant = null;
    }

}
