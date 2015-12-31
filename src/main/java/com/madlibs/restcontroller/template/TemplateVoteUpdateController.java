package com.madlibs.restcontroller.template;

import com.madlibs.data.DatabaseService;
import com.madlibs.model.MadLibsTemplate;
import com.madlibs.model.MadLibsTemplateRating;
import com.madlibs.model.RegisteredUser;
import com.madlibs.restcontroller.RestEndpoint;
import spark.Request;
import spark.Response;

/**
 * Template vote controller.
 * Created by Ran on 12/30/2015.
 */
public class TemplateVoteUpdateController extends RestEndpoint {

    /**
     * Constructs a controller to handle request.
     * @param request Spark request.
     * @param response Spark response.
     */
    public TemplateVoteUpdateController(Request request, Response response) {
        super(request, response);

        RegisteredUser user = getLoggedInUser();
        String templateId = request.params("id");
        String vote = parsedRequest.get("vote").getAsString();

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

        MadLibsTemplateRating rating = new MadLibsTemplateRating(user.getUsername(), templateId, vote);
        DatabaseService.getInstance().updateRating(rating);

        response.status(200);
        responseBody.addProperty("user", user.getUsername());
        responseBody.addProperty("id", templateId);
        responseBody.addProperty("voted", rating.getRating());

    }
}
