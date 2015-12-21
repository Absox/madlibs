package com.madlibs.model;

import com.madlibs.server.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A mad libs template.
 * Created by Ran on 12/20/2015.
 */
public class MadLibsTemplate {

    private UUID id;

    private User creator;

    private int rating;

    private List<TemplateLine> content;

    /**
     * Creates a new mad libs template.
     * @param creator
     */
    public MadLibsTemplate(User creator) {
        this.creator = creator;
        this.content = new ArrayList<>();
        this.id = UUID.randomUUID();
    }

    /**
     * Accessor for the rating of the template.
     * @return Rating of template.
     */
    public int getRating() {
        return this.rating;
    }

    /**
     * Accessor for the creator of the template.
     * @return Creator of the template.
     */
    public User getCreator() {
        return this.creator;
    }

    /**
     * A single line within the template.
     */
    private class TemplateLine {

    }

}
