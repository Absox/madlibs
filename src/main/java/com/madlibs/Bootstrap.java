package com.madlibs;

import com.madlibs.server.RestEndpoint;
import com.madlibs.server.TemplateCreateController;
import com.madlibs.server.UserLoginController;
import com.madlibs.server.UserRegisterController;

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

        // Serve static files.
        get("/game/*", (request, response) -> {
            return new String(Files.readAllBytes(Paths.get("www/index.html")));
        });

        // Delegate login call to user login controller.
        post("/madlibs/api/login", "application/json", (request, response) -> {
            RestEndpoint controller = new UserLoginController(request);
            response.status(controller.getResponseCode());
            return controller.getResponseBody();
        });

        // Delegate register call to user register controller.
        post("/madlibs/api/register", "application/json", (request, response) -> {
            RestEndpoint controller = new UserRegisterController(request);
            response.status(controller.getResponseCode());
            return controller.getResponseBody();
        });

        // Delegate template creation call to template create controller.
        post("/madlibs/api/template", "application/json", (request, response) -> {
            RestEndpoint controller = new TemplateCreateController(request);
            response.status(controller.getResponseCode());
            return controller.getResponseBody();
        });

    }
}
