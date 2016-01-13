package com.madlibs.restcontroller.template;

import com.madlibs.data.DatabaseService;
import com.madlibs.model.MadLibsTemplate;
import com.madlibs.model.RegisteredUser;
import com.madlibs.restcontroller.RestEndpoint;
import com.madlibs.server.MadLibsServer;
import spark.Request;
import spark.Response;

/**
 * Endpoint controller for template creation.
 * Created by Ran on 12/22/2015.
 */
public class TemplateCreateController extends RestEndpoint {

    /**
     * Creates a controller to handle a template creation request.
     * @param request Spark request.
     * @param response Spark response.
     */
    public TemplateCreateController(Request request, Response response) {
        super(request, response);

        RegisteredUser user = getLoggedInUser();
        String content = parsedRequest.get("value").getAsString();
        String title = parsedRequest.get("title").getAsString();

        // Check credentials.
        if (!authenticate() || user == null) {
            invalidCredentialFailure();
            return;
        }

        // Create template, add to database, update server configs.
        MadLibsTemplate newTemplate = new MadLibsTemplate(MadLibsServer.getInstance().getConfigs().getNextTemplateId(), title, user.getUsername(), content);
        if (newTemplate.getNumBlanks() == 0) {
            blankTemplateFailure();
            return;
        }

        DatabaseService.getInstance().addTemplate(newTemplate);
        DatabaseService.getInstance().updateServerConfigs(MadLibsServer.getInstance().getConfigs());

        response.status(200);
        responseBody.addProperty("status", "success");
        responseBody.addProperty("id", newTemplate.getId());
    }
}
