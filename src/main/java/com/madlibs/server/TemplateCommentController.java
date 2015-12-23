package com.madlibs.server;

import com.madlibs.data.DatabaseService;
import com.madlibs.model.MadLibsTemplate;
import com.madlibs.model.MadLibsTemplateComment;
import com.madlibs.model.RegisteredUser;
import spark.Request;
import spark.Response;

import java.util.Date;

/**
 * Controller that handles commenting on templates.
 * Created by Ran on 12/22/2015.
 */
public class TemplateCommentController extends RestEndpoint {

    /**
     * Construct a new controller to handle a template comment request.
     * @param request Spark request.
     * @param response Spark response.
     */
    public TemplateCommentController(Request request, Response response) {
        super(request, response);

        RegisteredUser user = getLoggedInUser();
        String templateId = request.params("id");
        String value = parsedRequest.get("value").getAsString();

        // Check failure cases.
        if (!authenticate() || user == null) {
            invalidCredentialFailure();
            return;
        }

        MadLibsTemplate template = DatabaseService.getInstance().getTemplate(templateId);

        // Check template existence.
        if (template == null) {
            nullResourceFailure();
            return;
        }

        MadLibsTemplateComment newComment = new MadLibsTemplateComment(templateId, user.getUsername(), value, new Date().getTime());
        DatabaseService.getInstance().addTemplateComment(newComment);

        response.status(200);
        responseBody.addProperty("status", "success");
        responseBody.addProperty("user", user.getUsername());
        responseBody.addProperty("id", templateId);
    }
}
