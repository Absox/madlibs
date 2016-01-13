package com.madlibs.restcontroller.template;

import com.madlibs.data.DatabaseService;
import com.madlibs.model.MadLibsTemplate;
import com.madlibs.model.RegisteredUser;
import com.madlibs.restcontroller.RestEndpoint;
import spark.Request;
import spark.Response;

/**
 * Controller for template update calls.
 * Created by Ran on 12/23/2015.
 */
public class TemplateUpdateController extends RestEndpoint {

    /**
     * Constructs an object to handle the request.
     * @param request Spark request
     * @param response Spark response
     */
    public TemplateUpdateController(Request request, Response response) {
        super(request, response);

        RegisteredUser user = getLoggedInUser();
        String templateId = request.params("id");
        String value = parsedRequest.get("value").getAsString();
        String title = parsedRequest.get("title").getAsString();

        // Check authentication.
        if (!authenticate() || user == null) {
            invalidCredentialFailure();
            return;
        }

        MadLibsTemplate template = DatabaseService.getInstance().getTemplate(templateId);

        // Check if template exists
        if (template == null) {
            nullResourceFailure();
            return;
        }
        // Check ownership.
        if (!template.getCreator().equals(user.getUsername())) {
            resourceNotOwnedFailure();
            return;
        }

        // Update template, success response
        template.setContent(value);
        template.setTitle(title);

        if (template.getNumBlanks() == 0) {
            blankTemplateFailure();
        }

        DatabaseService.getInstance().updateTemplate(template);

        response.status(200);
        responseBody.addProperty("status", "success");
        responseBody.addProperty("user", user.getUsername());
        responseBody.addProperty("id", templateId);
    }
}
