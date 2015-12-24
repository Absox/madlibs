package com.madlibs;

import com.madlibs.server.*;

import java.nio.file.Files;
import java.nio.file.Paths;

import static spark.Spark.*;

/**
 * Bootstrap - main method for madlibs REST api.
 * Created by Ran on 12/20/2015.
 */
public class Bootstrap {

    public static void main(String[] args) {

        port(3000);
        externalStaticFileLocation("www");

        // Set access origin header.
        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
        });

        // Serve static files.
        get("/game", (request, response) -> new String(Files.readAllBytes(Paths.get("www/index.html"))));
        get("/game/*", (request, response) -> new String(Files.readAllBytes(Paths.get("www/index.html"))));
        get("/script", (request, response) -> new String(Files.readAllBytes(Paths.get("www/index.html"))));
        get("/script/*", (request, response) -> new String(Files.readAllBytes(Paths.get("www/index.html"))));
        get("/templates", (request, response) -> new String(Files.readAllBytes(Paths.get("www/index.html"))));
        get("/templates/*", (request, response) -> new String(Files.readAllBytes(Paths.get("www/index.html"))));
        get("/account", (request, response) -> new String(Files.readAllBytes(Paths.get("www/index.html"))));
        get("/account/*", (request, response) -> new String(Files.readAllBytes(Paths.get("www/index.html"))));
        get("/select-template", (request, response) -> new String(Files.readAllBytes(Paths.get("www/index.html"))));
        get("/select-template/*", (request, response) -> new String(Files.readAllBytes(Paths.get("www/index.html"))));
        get("/signup", (request, response) -> new String(Files.readAllBytes(Paths.get("www/index.html"))));
        get("/signup/*", (request, response) -> new String(Files.readAllBytes(Paths.get("www/index.html"))));
        get("/login", (request, response) -> new String(Files.readAllBytes(Paths.get("www/index.html"))));
        get("/login/*", (request, response) -> new String(Files.readAllBytes(Paths.get("www/index.html"))));

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

    }
}
