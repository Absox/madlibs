package com.madlibs.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ran on 12/22/2015.
 */
public class MadLibsScript {

    private String id;
    private MadLibsTemplate template;
    private List<MadLibsResponse> responses;

    /**
     * Creates a new blank mad libs script.
     * @param template
     */
    public MadLibsScript(MadLibsTemplate template, String id) {
        this.id = id;
        this.template = template;
        this.responses = new ArrayList<>();
    }

    /**
     * Accessor for id.
     * @return Id of the script.
     */
    public String getId() {
        return this.id;
    }

    /**
     * Accessor for template.
     * @return Template associated with this script.
     */
    public MadLibsTemplate getTemplate() {
        return this.template;
    }

    /**
     * Accessor for responses.
     * @return Responses.
     */
    public List<MadLibsResponse> getResponses() {
        return this.responses;
    }

}
