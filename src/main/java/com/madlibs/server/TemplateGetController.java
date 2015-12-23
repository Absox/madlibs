package com.madlibs.server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.madlibs.data.DatabaseService;
import com.madlibs.model.MadLibsTemplate;
import com.madlibs.model.MadLibsTemplateComment;
import spark.Request;
import spark.Response;

import java.util.List;

/**
 * Controller to handle template get requests.
 * Created by Ran on 12/23/2015.
 */
public class TemplateGetController implements RestEndpoint {

    private JsonObject responseBody;

    /**
     * Constructs a controller to handle the request.
     * @param request Request to handle.
     * @param response Spark response.
     */
    public TemplateGetController(Request request, Response response) {
        String templateId = request.params("id");

        if (DatabaseService.getInstance().templateExists(templateId)) {
            MadLibsTemplate template = DatabaseService.getInstance().getTemplate(templateId);
            List<MadLibsTemplateComment> comments = DatabaseService.getInstance().getCommentsOnTemplate(template.getId());

            response.status(200);
            responseBody = templateToJson(template, comments);
        } else {
            this.responseBody = new JsonObject();
            response.status(404);
            responseBody.addProperty("status", "failure");
            responseBody.addProperty("why", "Template not found");
        }
    }

    /**
     * Accessor for response body.
     * @return Response body.
     */
    public JsonObject getResponseBody() {
        return this.responseBody;
    }

    /**
     * Converts a template and list of comments to json.
     * @param template Template.
     * @param comments Comments.
     * @return Json object.
     */
    public static JsonObject templateToJson(MadLibsTemplate template, List<MadLibsTemplateComment> comments) {
        TemplateBodyJson json = new TemplateBodyJson(template, comments);
        return new Gson().toJsonTree(json).getAsJsonObject();
    }

    private static class TemplateBodyJson {

        private String status;
        private String title;
        private String creator;
        private int rating;
        private String value;
        private List<MadLibsTemplateComment> comments;

        private TemplateBodyJson(MadLibsTemplate template, List<MadLibsTemplateComment> comments) {
            this.status = "success";
            this.title = template.getTitle();
            this.creator = template.getCreator();
            this.rating = template.getRating();
            this.value = template.getContent();
            this.comments = comments;
        }
    }
}
