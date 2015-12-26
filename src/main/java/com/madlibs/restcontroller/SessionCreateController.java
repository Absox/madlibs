package com.madlibs.restcontroller;

import com.madlibs.data.DatabaseService;
import com.madlibs.model.MadLibsTemplate;
import com.madlibs.model.RegisteredUser;
import com.madlibs.server.MadLibsServer;
import spark.Request;
import spark.Response;

/**
 * Session creation controller.
 * Created by Ran on 12/25/2015.
 */
public class SessionCreateController extends RestEndpoint {

    public SessionCreateController(Request request, Response response) {
        super(request, response);

        RegisteredUser user = getLoggedInUser();
        String templateId = parsedRequest.get("templateId").getAsString();

        if (!authenticate() || user == null) {
            invalidCredentialFailure();
            return;
        }

        MadLibsTemplate template = DatabaseService.getInstance().getTemplate(templateId);

        // Template doesn't exist.
        if (template == null) {
            nullResourceFailure();
            return;
        }

        // Create script and add to server
        String scriptId = Integer.toHexString(MadLibsServer.getInstance().getConfigs().getNextScriptId());
        MadLibsServer.getInstance().createSession(scriptId, user.getUsername(), template);
        DatabaseService.getInstance().updateServerConfigs(MadLibsServer.getInstance().getConfigs());

        response.status(200);
        responseBody.addProperty("status", "success");
        responseBody.addProperty("sessionId", scriptId);
    }
}
