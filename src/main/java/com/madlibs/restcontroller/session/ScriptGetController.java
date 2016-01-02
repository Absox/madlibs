package com.madlibs.restcontroller.session;

import com.madlibs.data.DatabaseService;
import com.madlibs.model.MadLibsScript;
import com.madlibs.restcontroller.RestEndpoint;
import spark.Request;
import spark.Response;

/**
 * Script retrieval controller.
 * Created by Ran on 1/2/2016.
 */
public class ScriptGetController extends RestEndpoint {

    /**
     * Construct a controller to handle the request.
     * @param request Spark request
     * @param response Spark response
     */
    public ScriptGetController(Request request, Response response) {
        super(request, response);

        String id = request.params("id");
        MadLibsScript script = DatabaseService.getInstance().getScript(id);

        if (script == null) {
            nullResourceFailure();
            return;
        }

        response.status(200);
        responseBody = script.getScriptJson();
    }
}
