package com.madlibs;

import com.madlibs.restcontroller.*;
import com.madlibs.websocketcontroller.WebsocketController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static spark.Spark.*;

/**
 * Bootstrap - main method for madlibs REST api.
 * Created by Ran on 12/20/2015.
 */
public class Bootstrap {

    public static void main(String[] args) {

        port(80);

        // Bind websocket route
        webSocket("/madlibs/api/websocket", WebsocketController.class);

        // Set static file base.
        externalStaticFileLocation("www");

        // Set access origin header.
        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
        });

        // Serve static files.
        get("/game", (request, response) -> getFileContentsAsString("www/index.html"));
        get("/game/*", (request, response) -> getFileContentsAsString("www/index.html"));
        get("/script", (request, response) -> getFileContentsAsString("www/index.html"));
        get("/script/*", (request, response) -> getFileContentsAsString("www/index.html"));
        get("/templates", (request, response) -> getFileContentsAsString("www/index.html"));
        get("/template/*", (request, response) -> getFileContentsAsString("www/index.html"));
        get("/account", (request, response) -> getFileContentsAsString("www/index.html"));
        get("/account/*", (request, response) -> getFileContentsAsString("www/index.html"));
        get("/select-template", (request, response) -> getFileContentsAsString("www/index.html"));
        get("/select-template/*", (request, response) -> getFileContentsAsString("www/index.html"));
        get("/signup", (request, response) -> getFileContentsAsString("www/index.html"));
        get("/signup/*", (request, response) -> getFileContentsAsString("www/index.html"));
        get("/login", (request, response) -> getFileContentsAsString("www/index.html"));
        get("/login/*", (request, response) -> getFileContentsAsString("www/index.html"));

        // User login call
        post("/madlibs/api/login", "application/json", (request, response) -> new UserLoginController(request, response).getResponseBody());
        // User register call
        post("/madlibs/api/register", "application/json", (request, response) -> new UserRegisterController(request, response).getResponseBody());
        // Template create call
        post("/madlibs/api/template", "application/json", (request, response) -> new TemplateCreateController(request, response).getResponseBody());
        // Template comment call
        post("/madlibs/api/template/:id/comment", "application/json", (request, response) -> new TemplateCommentController(request, response).getResponseBody());
        // Template update call
        put("/madlibs/api/template/:id", "application/json", (request, response) -> new TemplateUpdateController(request, response).getResponseBody());
        // Template delete call
        delete("/madlibs/api/template/:id", "application/json", (request, response) -> new TemplateDeleteController(request, response).getResponseBody());
        // Template body get call
        get("/madlibs/api/template/:id", "application/json", (request, response) -> new TemplateGetController(request, response).getResponseBody());
        // Get list of templates for user
        get("/madlibs/api/template/user/:username", "application/json", (request, response) -> new TemplateGetForUserController(request, response).getResponseBody());
        // Create new game session
        post("/madlibs/api/session", "application/json", (request, response) -> new SessionCreateController(request, response).getResponseBody());
    }

    /**
     * Gets file contents as string.
     * @param filename Path to file.
     * @return String containing the contents of the file.
     * @throws IOException Error in reading contents.
     */
    private static String getFileContentsAsString(String filename) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filename)));
    }
}
