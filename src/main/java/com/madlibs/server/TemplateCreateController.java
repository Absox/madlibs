package com.madlibs.server;

import com.google.gson.JsonObject;
import spark.Request;
import spark.Response;

/**
 * Endpoint controller for template creation.
 * Created by Ran on 12/22/2015.
 */
public class TemplateCreateController implements RestEndpoint {

    public TemplateCreateController(Request request, Response response) {

    }

    public JsonObject getResponseBody() {
        return null; // TODO
    }
}
