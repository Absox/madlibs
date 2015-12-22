package com.madlibs;

import java.nio.file.Files;
import java.nio.file.Paths;

import static spark.Spark.*;

/**
 * Created by Ran on 12/20/2015.
 */
public class Bootstrap {

    public static void main(String[] args) {

        port(3000);
        externalStaticFileLocation("www");

        get("/game/*", (request, response) -> {
            return new String(Files.readAllBytes(Paths.get("www/index.html")));
        });

    }
}
