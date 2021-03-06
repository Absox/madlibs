package com.madlibs.restcontroller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.madlibs.authentication.AuthToken;
import com.madlibs.data.DatabaseService;
import com.madlibs.model.RegisteredUser;
import com.madlibs.server.MadLibsServer;
import spark.Request;
import spark.Response;

/**
 * Rest endpoint controller interface.
 * Created by Ran on 12/22/2015.
 */
public abstract class RestEndpoint {

    protected JsonParser parser = new JsonParser();
    protected Gson gson = new Gson();
    protected JsonObject parsedRequest;
    protected JsonObject responseBody;
    protected Request request;
    protected Response response;

    /**
     * Constructs a rest endpoint.
     * @param request Spark request.
     * @param response Spark response.
     */
    protected RestEndpoint(Request request, Response response) {
        this.request = request;
        this.response = response;
        if (!request.requestMethod().equals("GET")) {
            this.parsedRequest = parser.parse(request.body()).getAsJsonObject();
        }
        this.responseBody = new JsonObject();
    }

    /**
     * Gets response body.
     * @return Response body.
     */
    public JsonObject getResponseBody() {
        return this.responseBody;
    }

    /**
     * Issues an auth token, and sets logged in user.
     * @param username Username for whom to issue auth token.
     */
    protected void issueAuthToken(String username) {
        AuthToken token = MadLibsServer.getInstance().issueToken(username);
        responseBody.add("authToken", gson.toJsonTree(token));
    }

    /**
     * Gets logged in user object, if one exists.
     * @return Logged in user object, else null.
     */
    protected RegisteredUser getLoggedInUser() {
        JsonElement authTokenJson = parsedRequest.get("authToken");
        if (authTokenJson != null) {
            AuthToken authToken = gson.fromJson(authTokenJson, AuthToken.class);
            return DatabaseService.getInstance().getUser(authToken.getUsername());
        }
        return null;
    }

    /**
     * Authenticates the user using the authentication token.
     * @return True on successful authentication. False on failure.
     */
    protected boolean authenticate() {
        JsonElement authTokenJson = parsedRequest.get("authToken");

        if (authTokenJson != null) {
            AuthToken authToken = gson.fromJson(authTokenJson, AuthToken.class);
            AuthToken newToken = MadLibsServer.getInstance().authenticate(authToken);

            if (newToken != null) {
                responseBody.add("authToken", gson.toJsonTree(newToken));
                return true;
            }
        }

        //response.removeCookie("authToken");

        return false;
    }

    /**
     * A null user failure.
     */
    protected void nullUserFailure() {
        responseBody.addProperty("status", "failure");
        responseBody.addProperty("why", "User does not exist");
    }

    /**
     * Failure due to invalid credentials.
     */
    protected void invalidCredentialFailure() {
        response.status(401);
        responseBody.addProperty("status", "failure");
        responseBody.addProperty("why", "Invalid credentials");
    }

    /**
     * Failure due to user trying to modify a resource they do not own.
     */
    protected void resourceNotOwnedFailure() {
        response.status(401);
        responseBody.addProperty("status", "failure");
        responseBody.addProperty("why", "Resource not owned");
    }

    /**
     * Failure due to user trying to access or modify a resource that doesn't exist.
     */
    protected void nullResourceFailure() {
        response.status(404);
        responseBody.addProperty("status", "failure");
        responseBody.addProperty("why", "Resource doesn't exist");
    }

    protected void blankTemplateFailure() {
        response.status(200);
        responseBody.addProperty("status", "failure");
        responseBody.addProperty("why", "Template has no blanks");
    }
}
