package com.madlibs.restcontroller.template;

import com.madlibs.data.DatabaseService;
import com.madlibs.model.MadLibsTemplate;
import com.madlibs.model.MadLibsTemplateRating;
import com.madlibs.model.RegisteredUser;
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

        RegisteredUser user = getLoggedInUser();
        String templateId = request.params("id");

        // Check authentication.
        if (!authenticate() || user == null) {
            invalidCredentialFailure();
            return;
        }

        MadLibsTemplate template = DatabaseService.getInstance().getTemplate(templateId);

        if (template == null) {
            nullResourceFailure();
            return;
        }

        MadLibsTemplateRating rating = DatabaseService.getInstance().getRating(user.getUsername(), templateId);

        response.status(200);
        responseBody.addProperty("status", "success");

        if (rating != null) {
            responseBody.addProperty("voted", rating.getRating());
        } else {
            responseBody.addProperty("voted", "none");
        }

    }

}
