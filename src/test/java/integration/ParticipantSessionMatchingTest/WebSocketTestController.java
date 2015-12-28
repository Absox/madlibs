package integration.ParticipantSessionMatchingTest;

import com.madlibs.model.MadLibsSessionParticipant;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
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
    }

}
