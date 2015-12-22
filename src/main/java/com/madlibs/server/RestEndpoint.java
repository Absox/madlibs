package com.madlibs.server;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Rest endpoint controller interface.
 * Created by Ran on 12/22/2015.
 */
public interface RestEndpoint {

    JsonParser parser = new JsonParser();

    /**
     * Gets response code.
     * @return Response code.
     */
    int getResponseCode();

    /**
     * Gets response body.
     * @return Response body.
     */
    JsonObject getResponseBody();
}
