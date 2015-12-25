package com.madlibs.restcontroller;

import com.madlibs.data.DatabaseService;
import com.madlibs.model.RegisteredUser;
import org.apache.commons.codec.DecoderException;
import spark.Request;
import spark.Response;

import java.io.IOException;

/**
 * Controller for handling user login requests.
 * Created by Ran on 12/22/2015.
 */
public class UserLoginController extends RestEndpoint {

    /**
     * Creates a controller to handle the request.
     * @param request Spark request.
     */
    public UserLoginController(Request request, Response response) throws IOException, DecoderException {
        super(request, response);

        // Set response code.
        response.status(200);

        String username = parsedRequest.get("user").getAsString().toLowerCase();
        String password = parsedRequest.get("password").getAsString();

        RegisteredUser databaseUser = DatabaseService.getInstance().getUser(username);

        // Check for failure cases:
        if (databaseUser == null) {
            nullUserFailure();
            return;
        }
        if (!databaseUser.validatePassword(password)) {
            invalidCredentialFailure();
            return;
        }

        // Valid login.
        issueAuthToken(username);
        responseBody.addProperty("status", "success");
        responseBody.addProperty("user", username);
    }
}
