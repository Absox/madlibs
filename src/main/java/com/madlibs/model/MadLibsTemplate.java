package com.madlibs.model;

/**
 * A mad libs template.
 * Created by Ran on 12/20/2015.
 */
public class MadLibsTemplate {

    private String id;

    private String creator;

    private int rating;

    private String content;

    /**
     * Initializes a blank template object.
     * @param id Integer id of object.
     * @param creator Username of creator.
     */
    public MadLibsTemplate(int id, String creator) {
        this(Integer.toHexString(id), creator, 0, "");
    }

    /**
     * Creates a mad libs template object.
     * @param id Id of template.
     * @param creator Username of creator.
     * @param rating Rating.
     * @param content Content of template.
     */
    public MadLibsTemplate(String id, String creator, int rating, String content) {
        this.id = id;
        this.creator = creator;
        this.rating = rating;
        this.content = content;
    }

    /**
     * Accessor for id.
     * @return Id of template.
     */
    public String getId() {
        return this.id;
    }

    /**
     * Accessor for creator.
     * @return Creator of template.
     */
    public String getCreator() {
        return this.creator;
    }

    /**
     * Accessor for rating.
     * @return Rating of template.
     */
    public int getRating() {
        return this.rating;
    }

    /**
     * Accessor for template content.
     * @return Content of template.
     */
    public String getContent() {
        return this.content;
    }


}
