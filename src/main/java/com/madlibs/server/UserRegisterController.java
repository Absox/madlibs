package com.madlibs.server;

import com.google.gson.JsonObject;
import com.madlibs.data.DatabaseService;
import com.madlibs.model.RegisteredUser;
import spark.Request;

import java.io.IOException;

/**
 * Controller for user register request.
 * Created by Ran on 12/22/2015.
 */
public class UserRegisterController implements RestEndpoint {

    private int responseCode = 200;
    private JsonObject responseBody;

    /**
     * Creates a controller to handle a user register request.
     * @param request Spark request.
     * @throws IOException
     */
    public UserRegisterController(Request request) throws IOException {
        this.responseBody = new JsonObject();
        JsonObject requestObject = parser.parse(request.body()).getAsJsonObject();
        String username = requestObject.get("user").getAsString().toLowerCase();
        String password = requestObject.get("password").getAsString();

        if (DatabaseService.getInstance().userExists(username)) {
            // User already exists!
            responseBody.addProperty("status", "failure");
            responseBody.addProperty("why", "Username is taken");
        } else {
            // Register user
            RegisteredUser newUser = new RegisteredUser(username, password);
            DatabaseService.getInstance().addUser(newUser);

            responseBody.addProperty("status", "success");
            responseBody.addProperty("user", username);
        }
    }

    /**
     * Accessor for response code.
     * @return Response code.
     */
    public int getResponseCode() {
        return this.responseCode;
    }

    /**
     * Accessor for response body.
     * @return Response body.
     */
    public JsonObject getResponseBody() {
        return this.responseBody;
    }


}
