package com.madlibs.server;

import com.google.gson.JsonObject;
import com.madlibs.data.DatabaseService;
import com.madlibs.model.MadLibsTemplate;
import spark.Request;
import spark.Response;

/**
 * Endpoint controller for template creation.
 * Created by Ran on 12/22/2015.
 */
public class TemplateCreateController implements RestEndpoint {

    private JsonObject responseBody;

    /**
     * Creates a controller to handle a template creation request.
     * @param request Spark request.
     * @param response Spark response.
     */
    public TemplateCreateController(Request request, Response response) {
        this.responseBody = new JsonObject();
        String username = request.cookie("loggedInUser");

        JsonObject parsedRequest = parser.parse(request.body()).getAsJsonObject();
        String content = parsedRequest.get("value").getAsString();
        String title = parsedRequest.get("title").getAsString();
        if (username != null) {

            // Check if user exists.
            if (DatabaseService.getInstance().userExists(username)) {
                // Create template.
                MadLibsTemplate newTemplate = new MadLibsTemplate(MadLibsServer.getInstance().getConfigs().getNextTemplateId(), title, username, content);
                // Add template to database.
                DatabaseService.getInstance().addTemplate(newTemplate);
                // Update server configs in database.
                DatabaseService.getInstance().updateServerConfigs(MadLibsServer.getInstance().getConfigs());

                response.status(200);
                responseBody.addProperty("status", "success");
                responseBody.addProperty("id", newTemplate.getId());

            } else {
                response.status(401);
                responseBody.addProperty("status", "failure");
                responseBody.addProperty("why", "Unauthorized - user does not exist");
            }

        } else {
            // Unauthorized - user not logged in
            response.status(401);
            responseBody.addProperty("status", "failure");
            responseBody.addProperty("why", "Unauthorized - not logged in");
        }

    }

    /**
     * Accessor to response body.
     * @return Response body.
     */
    public JsonObject getResponseBody() {
        return this.responseBody;
    }
}
