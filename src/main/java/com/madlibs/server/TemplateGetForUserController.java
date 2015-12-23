package com.madlibs.server;

import com.google.gson.JsonObject;
import spark.Request;
import spark.Response;

/**
 * Controller for get requests for templates created by user.
 * Created by Ran on 12/23/2015.
 */
public class TemplateGetForUserController implements RestEndpoint {

    private JsonObject responseBody;

    public TemplateGetForUserController(Request request, Response response) {

    }

    public JsonObject getResponseBody() {
        return this.responseBody;
    }
}
