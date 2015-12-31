package com.madlibs.restcontroller.template;

import com.madlibs.restcontroller.RestEndpoint;
import spark.Request;
import spark.Response;

/**
 * Template voting status get controller.
 * Created by Ran on 12/30/2015.
 */
public class TemplateVoteGetController extends RestEndpoint {

    /**
     * Constructs a controller to handle the request.
     * @param request Spark request.
     * @param response Spark response.
     */
    public TemplateVoteGetController(Request request, Response response) {
        super(request, response);


    }

}
