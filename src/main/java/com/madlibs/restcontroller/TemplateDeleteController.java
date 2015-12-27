package com.madlibs.restcontroller;

import com.madlibs.data.DatabaseService;
import com.madlibs.model.MadLibsTemplate;
import com.madlibs.model.RegisteredUser;
import spark.Request;
import spark.Response;

/**
 * A controller for handling template deletion requests.
 * Created by Ran on 12/23/2015.
 */
public class TemplateDeleteController extends RestEndpoint {

    /**
     * Constructs a controller to handle the template deletion request.
     * @param request Spark request.
     * @param response Spark response.
     */
    public TemplateDeleteController(Request request, Response response) {
        super(request, response);

        RegisteredUser user = getLoggedInUser();
        String templateId = request.params("id");

        // Check authentication
        if (!authenticate() || user == null) {
            invalidCredentialFailure();
            return;
        }

        MadLibsTemplate template = DatabaseService.getInstance().getTemplate(templateId);

        // Check that template exists
        if (template == null) {
            nullResourceFailure();
            return;
        }

        // Check ownership
        if (!template.getCreator().equals(user.getUsername())) {
            System.out.println("Failure: resource owned by " + template.getCreator());
            System.out.println("Logged in as " + user.getUsername());
            resourceNotOwnedFailure();
            return;
        }

        // Delete template.
        DatabaseService.getInstance().deleteTemplate(templateId);
        response.status(200);
        responseBody.addProperty("status", "success");
        responseBody.addProperty("user", user.getUsername());
    }
}
