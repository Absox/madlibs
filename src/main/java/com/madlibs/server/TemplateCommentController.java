package com.madlibs.server;

import com.google.gson.JsonObject;
import com.madlibs.data.DatabaseService;
import com.madlibs.model.MadLibsTemplateComment;
import spark.Request;
import spark.Response;

import java.util.Date;

/**
 * Created by Ran on 12/22/2015.
 */
public class TemplateCommentController implements RestEndpoint {

    private JsonObject responseBody;

    public TemplateCommentController(Request request, Response response) {

        this.responseBody = new JsonObject();
        String username = request.cookie("loggedInUser");
        String templateId = request.params("id");
        String content = parser.parse(request.body()).getAsJsonObject().get("content").getAsString();

        if (username != null) {
            if (DatabaseService.getInstance().userExists(username)) {
                if (DatabaseService.getInstance().templateExists(templateId)) {
                    MadLibsTemplateComment newComment = new MadLibsTemplateComment(templateId, username, content, new Date().getTime());
                    // Add comment to database.
                    DatabaseService.getInstance().addTemplateComment(newComment);

                    response.status(200);
                    responseBody.addProperty("status", "success");
                    responseBody.addProperty("user", username);
                    responseBody.addProperty("id", templateId);
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
     * @return Response body.
     */
    public JsonObject getResponseBody() {
        return this.responseBody;
    }

}
