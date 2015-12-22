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
        get("/game*", (request, response) -> {
            return new String(Files.readAllBytes(Paths.get("www/index.html")));
        });
        get("/script*", (request, response) -> {
            return new String(Files.readAllBytes(Paths.get("www/index.html")));
        });
        get("/template*", (request, response) -> {
            return new String(Files.readAllBytes(Paths.get("www/index.html")));
        });

        // User login call
        post("/madlibs/api/login", "application/json", (request, response) -> {
            RestEndpoint controller = new UserLoginController(request, response);
            return controller.getResponseBody();
        });

        // User register call
        post("/madlibs/api/register", "application/json", (request, response) -> {
            RestEndpoint controller = new UserRegisterController(request, response);
            return controller.getResponseBody();
        });

        // Template create call
        post("/madlibs/api/template", "application/json", (request, response) -> {
            RestEndpoint controller = new TemplateCreateController(request, response);
            return controller.getResponseBody();
        });

        // Template update call
        put("/madlibs/api/template/:id", "application/json", (request, response) -> {
            return ""; // TODO
        });

        // Template delete call
        delete("/madlibs/api/template/:id", "application/json", (request, response) -> {
            return ""; // TODO
        });

        // Template body get call
        get("/madlibs/api/template/:id", "applicaton/json", (request, response) -> {
            return ""; // TODO
        });

        // Get list of templates for user
        get("/madlibs/api/template/user/:username", "application/json", (request, response) -> {
            return ""; // TODO
        });

    }
}
