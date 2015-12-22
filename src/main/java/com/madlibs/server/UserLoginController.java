package com.madlibs.server;

import com.google.gson.JsonObject;
import com.madlibs.data.DatabaseService;
import com.madlibs.model.RegisteredUser;
import org.apache.commons.codec.DecoderException;
import spark.Request;

import java.io.IOException;

/**
 * Controller for handling user login requests.
 * Created by Ran on 12/22/2015.
 */
public class UserLoginController implements RestEndpoint {

    private int responseCode = 200;
    private JsonObject responseBody;

    /**
     * Creates a controller to handle the request.
     * @param request Spark request.
     */
    public UserLoginController(Request request) throws IOException, DecoderException {
        this.responseBody = new JsonObject();
        JsonObject requestObject = parser.parse(request.body()).getAsJsonObject();
        String username = requestObject.get("user").getAsString().toLowerCase();
        String password = requestObject.get("password").getAsString();

        if (DatabaseService.getInstance().userExists(username)) {
            RegisteredUser user = DatabaseService.getInstance().getUser(username);
            if (user.validatePassword(password)) {
                // Valid login.
                responseBody.addProperty("status", "success");
                responseBody.addProperty("user", username);
            } else {
                // Invalid password
                responseBody.addProperty("status", "failure");
                responseBody.addProperty("why", "Invalid password");
            }
        } else {
            // User doesn't exist.
            responseBody.addProperty("status", "failure");
            responseBody.addProperty("why", "User doesn't exist");
        }
    }

    /**
     * Accessor for response code that should be given.
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
