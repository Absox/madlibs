package com.madlibs.server;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.madlibs.data.DatabaseService;
import com.madlibs.model.MadLibsTemplate;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for get requests for templates created by user.
 * Created by Ran on 12/23/2015.
 */
public class TemplateGetForUserController extends RestEndpoint {

    /**
     * Constructs a controller to handle request.
     * @param request Spark request.
     * @param response Spark response.
     */
    public TemplateGetForUserController(Request request, Response response) {
        super(request, response);

        String username = request.params("username").toLowerCase();
        List<MadLibsTemplate> templates = DatabaseService.getInstance().getListOfTemplatesForUser(username);

        response.status(200);
        responseBody.addProperty("status", "success");
        responseBody.add("templates", listToJson(templates));

    }

    /**
     * Converts a list of templates to a json element.
     * @param templates Templates to convert.
     * @return JsonElement representing the list of templates.
     */
    public static JsonElement listToJson(List<MadLibsTemplate> templates) {
        Gson gson = new Gson();
        List<TemplateJson> jsonTemplates = new ArrayList<>();
        for (MadLibsTemplate t : templates) {
            jsonTemplates.add(new TemplateJson(t));
        }
        return gson.toJsonTree(jsonTemplates);
    }

    /**
     * Container class to assist creation of Json.
     */
    private static class TemplateJson {

        private String title;
        private String id;

        private TemplateJson(MadLibsTemplate template) {
            this.title = template.getTitle();
            this.id = template.getId();
        }

    }
}
