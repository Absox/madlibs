package com.madlibs.model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.List;

/**
 * Object representation of a script (completed template)
 * Created by Ran on 1/2/2016.
 */
public class MadLibsScript {

    private String id;
    private String user;
    private String template;
    private List<MadLibsResponse> responses;
    private List<ChatMessage> chatlog;

    /**
     * Constructs a script object from all components.
     * @param id Id of script.
     * @param user User owner of script.
     * @param template Template content.
     * @param responses List of responses.
     * @param chatlog Chatlog.
     */
    public MadLibsScript(String id, String user, String template, List<MadLibsResponse> responses, List<ChatMessage> chatlog) {
        this.id = id;
        this.user = user;
        this.template = template;
        this.responses = responses;
        this.chatlog = chatlog;
    }

    /**
     * Creates a script from a session.
     * @param session Game session.
     */
    public MadLibsScript(MadLibsSession session) {
        this.id = session.getId();
        this.user = session.getHost();
        this.template = session.getTemplateContent();
        this.responses = session.getResponses();
        this.chatlog = session.getChatLog();
    }

    /**
     * Accessor for id of script.
     * @return Id of script.
     */
    public String getId() {
        return this.id;
    }

    /**
     * Accessor for user who owns script.
     * @return User who owns script.
     */
    public String getUser() {
        return this.user;
    }

    /**
     * Accessor for contents of template.
     * @return Contents of template.
     */
    public String getTemplateContent() {
        return this.template;
    }

    /**
     * Accessor for list of responses.
     * @return List of responses.
     */
    public List<MadLibsResponse> getResponses() {
        return this.responses;
    }

    /**
     * Accessor for json serialization of responses.
     * @return Json serialized responses.
     */
    public String getResponsesJson() {
        Gson gson = new Gson();

        return gson.toJson(this.responses);
    }

    /**
     * Accessor for chatlog.
     * @return Chatlog.
     */
    public List<ChatMessage> getChatlog() {
        return this.chatlog;
    }

    /**
     * Accessor for json serialization of chatlog.
     * @return Json serialization of chatlog.
     */
    public String getChatlogJson() {
        Gson gson = new Gson();
        return gson.toJson(this.chatlog);
    }

    /**
     * Json object.
     * @return Json object.
     */
    public JsonObject getScriptJson() {
        Gson gson = new Gson();
        return gson.toJsonTree(this).getAsJsonObject();
    }
}
