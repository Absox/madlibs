package com.madlibs.server;

import com.google.gson.JsonObject;
import spark.Request;

/**
 * Endpoint controller for template creation.
 * Created by Ran on 12/22/2015.
 */
public class TemplateCreateController implements RestEndpoint {

    public TemplateCreateController(Request request) {

    }

    public int getResponseCode() {
        return 0; // TODO
    }

    public JsonObject getResponseBody() {
        return null; // TODO
    }
}
