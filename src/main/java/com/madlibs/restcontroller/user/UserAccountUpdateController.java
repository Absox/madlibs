package com.madlibs.restcontroller.user;

import com.madlibs.data.DatabaseService;
import com.madlibs.model.RegisteredUser;
import com.madlibs.restcontroller.RestEndpoint;
import spark.Request;
import spark.Response;

import java.io.IOException;

/**
 *
 * Created by Ran on 1/1/2016.
 */
public class UserAccountUpdateController extends RestEndpoint {

    /**
     * Construct a controller to handle the request.
     * @param request Spark request.
     * @param response Spark response.
     */
    public UserAccountUpdateController(Request request, Response response) throws IOException {
        super(request, response);

        RegisteredUser user = getLoggedInUser();
        String newPassword = parsedRequest.get("password").getAsString();

        if (user == null || !authenticate()) {
            invalidCredentialFailure();
            return;
        }

        user.setPassword(newPassword);
        DatabaseService.getInstance().updateUser(user);

        response.status(200);
        responseBody.addProperty("status", "success");
        responseBody.addProperty("user", user.getUsername());
    }

}
