package com.madlibs.server;

import com.google.gson.JsonObject;
import com.madlibs.data.DatabaseService;
import com.madlibs.model.MadLibsTemplate;
import spark.Request;
import spark.Response;

/**
 * Controller for template update calls.
 * Created by Ran on 12/23/2015.
 */
public class TemplateUpdateController implements RestEndpoint {

    private JsonObject responseBody;

    /**
     * Constructs an object to handle the request.
     * @param request Spark request
     * @param response Spark response
     */
    public TemplateUpdateController(Request request, Response response) {

        this.responseBody = new JsonObject();
        String username = request.cookie("loggedInUser");
        String templateId = request.params("id");
        String content = parser.parse(request.body()).getAsJsonObject().get("value").getAsString();

        // Check login cookie
        if (username != null) {
            // Check if user exists
            if (DatabaseService.getInstance().userExists(username)) {
                if (DatabaseService.getInstance().templateExists(templateId)) {

                    MadLibsTemplate template = DatabaseService.getInstance().getTemplate(templateId);

                    // Check if template is owned
                    if (template.getCreator().equals(username)) {
                        template.setContent(content);
                        DatabaseService.getInstance().updateTemplate(template);

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

    /**
     * Accessor for response body.
     * @return Body of response.
     */
    public JsonObject getResponseBody() {
        return this.responseBody;
    }

}
