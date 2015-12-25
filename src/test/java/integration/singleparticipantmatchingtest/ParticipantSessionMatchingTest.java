package integration.singleparticipantmatchingtest;

import static spark.Spark.init;
import static spark.Spark.port;
import static spark.Spark.webSocket;

/**
 * Created by Ran on 12/25/2015.
 */
public class ParticipantSessionMatchingTest {

    public static void main(String[] args) {

        port(80);
        webSocket("/madlibs/api/websocket", WebSocketTestController.class);
        init();


    }
}
