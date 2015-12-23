package com.madlibs.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A completed mad libs script.
 * Created by Ran on 12/22/2015.
 */
public class MadLibsScript {

    private String owner;
    private String id;
    private MadLibsTemplate template;
    private List<MadLibsResponse> responses;

    /**
     * Creates a new blank mad libs script.
     * @param template Template which we're making a script for.
     */
    public MadLibsScript(MadLibsTemplate template, String owner, String id) {
        this.id = id;
        this.owner = owner;
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
