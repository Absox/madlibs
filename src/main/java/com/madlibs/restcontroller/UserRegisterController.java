package com.madlibs.restcontroller;

import com.madlibs.data.DatabaseService;
import com.madlibs.model.RegisteredUser;
import spark.Request;
import spark.Response;

import java.io.IOException;

/**
 * Controller for user register request.
 * Created by Ran on 12/22/2015.
 */
public class UserRegisterController extends RestEndpoint {

    /**
     * Creates a controller to handle a user register request.
     * @param request Spark request.
     * @throws IOException
     */
    public UserRegisterController(Request request, Response response) throws IOException {
        super(request, response);

        // Set response code.
        response.status(200);

        String username = parsedRequest.get("user").getAsString().toLowerCase();
        String password = parsedRequest.get("password").getAsString();

        // Check for failure case
        if (DatabaseService.getInstance().userExists(username)) {
            responseBody.addProperty("status", "failure");
            responseBody.addProperty("why", "Username taken");
            return;
        }

        // Register user.
        RegisteredUser newUser = new RegisteredUser(username, password);
        DatabaseService.getInstance().addUser(newUser);
        issueAuthToken(username);
        responseBody.addProperty("status", "success");
        responseBody.addProperty("user", username);
    }
}
