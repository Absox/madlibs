package com.madlibs.server;

import spark.Request;
import spark.Response;

/**
 * Controller for get requests for templates created by user.
 * Created by Ran on 12/23/2015.
 */
public class TemplateGetForUserController extends RestEndpoint {

    /**
     * Constructs a controller to handle request.
     * @param request Spark request.
     * @param response Spark response.
     */
    public TemplateGetForUserController(Request request, Response response) {
        super(request, response);
        // TODO

    }
}
