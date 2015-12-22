package com.madlibs.model;

/**
 * Created by Ran on 12/22/2015.
 */
public class MadLibsTemplateComment {

    private String templateId;
    private String user;
    private String value;

    /**
     * Constructs a comment.
     * @param templateId Id of template commented on.
     * @param user User commenting.
     * @param value Value of comment.
     */
    public MadLibsTemplateComment(String templateId, String user, String value) {
        this.templateId = templateId;
        this.user = user;
        this.value = value;
    }

}
