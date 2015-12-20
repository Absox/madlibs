package com.madlibs;

import static spark.Spark.*;

/**
 * Created by Ran on 12/20/2015.
 */
public class Bootstrap {

    public static void main(String[] args) {

        port(8080);
        webSocket("/madlibs/api/websocket", SocketController.class);
        init();

    }
}
