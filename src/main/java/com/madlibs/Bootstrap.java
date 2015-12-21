package com.madlibs;

import static spark.Spark.externalStaticFileLocation;
import static spark.Spark.port;

/**
 * Created by Ran on 12/20/2015.
 */
public class Bootstrap {

    public static void main(String[] args) {

        port(8080);
        externalStaticFileLocation("html");

    }
}
