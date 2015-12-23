package com.madlibs.model;

/**
 * Template comment class.
 * Created by Ran on 12/22/2015.
 */
public class MadLibsTemplateComment {

    private String templateId;
    private String user;
    private String value;
    private long date;

    /**
     * Constructs a comment.
     * @param templateId Id of template commented on.
     * @param user User commenting.
     * @param value Value of comment.
     */
    public MadLibsTemplateComment(String templateId, String user, String value, long date) {
        this.templateId = templateId;
        this.user = user;
        this.value = value;
        this.date = date;
    }

    /**
     * Accessor for id of template this is a comment for.
     * @return Id of template.
     */
    public String getTemplateId() {
        return this.templateId;
    }

    /**
     * Accessor for username of commenter.
     * @return Username of commenter.
     */
    public String getUser() {
        return this.user;
    }

    /**
     * Accessor for value of comment.
     * @return Value of comment.
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Accessor for date in unix time.
     * @return Unix time date long.
     */
    public long getDate() {
        return this.date;
    }
}
