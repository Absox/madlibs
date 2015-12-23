package com.madlibs.server;

import com.google.gson.JsonObject;
import com.madlibs.data.DatabaseService;
import com.madlibs.model.MadLibsTemplate;
import spark.Request;
import spark.Response;

/**
 * A controller for handling template deletion requests.
 * Created by Ran on 12/23/2015.
 */
public class TemplateDeleteController implements RestEndpoint {

    private JsonObject responseBody;

    /**
     * Constructs a controller to handle the template deletion request.
     * @param request Spark request.
     * @param response Spark response.
     */
    public TemplateDeleteController(Request request, Response response) {

        this.responseBody = new JsonObject();
        String username = request.cookie("loggedInUser");
        String templateId = request.params("id");

        // Check login cookie
        if (username != null) {
            // Check if user exists
            if (DatabaseService.getInstance().userExists(username)) {
                if (DatabaseService.getInstance().templateExists(templateId)) {

                    MadLibsTemplate template = DatabaseService.getInstance().getTemplate(templateId);

                    // Check if template is owned
                    if (template.getCreator().equals(username)) {
                       DatabaseService.getInstance().deleteTemplate(templateId);

                        response.status(200);
                        responseBody.addProperty("status", "success");
                        responseBody.addProperty("user", username);
                        responseBody.addProperty("id", templateId);
                    } else {
                        response.status(401);
                        responseBody.addProperty("status", "failure");
                        responseBody.addProperty("why", "Unauthorized - template not owned");
                    }
                } else {
                    response.status(404);
                    responseBody.addProperty("status", "failure");
                    responseBody.addProperty("why", "Template does not exist");
                }
            } else {
                response.status(401);
                responseBody.addProperty("status", "failure");
                responseBody.addProperty("why", "Unauthorized - user does not exist");
            }
        } else {
            response.status(401);
            responseBody.addProperty("status", "failure");
            responseBody.addProperty("why", "Unauthorized - not logged in");
        }
    }

    public JsonObject getResponseBody() {
        return this.responseBody;
    }
}
