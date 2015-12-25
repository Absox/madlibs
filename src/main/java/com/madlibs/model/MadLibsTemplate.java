package com.madlibs.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A mad libs template.
 * Created by Ran on 12/20/2015.
 */
public class MadLibsTemplate {

    private String id;

    private String title;

    private String creator;

    private int rating;

    private String content;

    /**
     * Initializes a blank template object.
     * @param id Integer id of object.
     * @param creator Username of creator.
     */
    public MadLibsTemplate(int id, String title, String creator, String content) {
        this(Integer.toHexString(id), title, creator, 0, content);
    }

    /**
     * Creates a mad libs template object.
     * @param id Id of template.
     * @param creator Username of creator.
     * @param rating Rating.
     * @param content Content of template.
     */
    public MadLibsTemplate(String id, String title, String creator, int rating, String content) {
        this.id = id;
        this.title = title;
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
     * Accessor for title.
     * @return Title (human-friendly) of template.
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Mutator for title.
     * @param title New title of template.
     */
    public void setTitle(String title) {
        this.title = title;
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

    /**
     * Mutator for template content.
     * @param content New content of template.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Returns the number of blanks in this template.
     * @return Number of blanks in the template.
     */
    public int getNumBlanks() {
        Pattern pattern = Pattern.compile("\\[[^\\[]*\\]");
        Matcher matcher = pattern.matcher(this.content);

        int numBlanks = 0;

        while (matcher.find()) {
            numBlanks++;
        }
        return numBlanks;
    }

    /**
     * Returns a list of the prompts in this template.
     * @return List of prompts in this template.
     */
    public List<String> getPrompts() {
        Pattern pattern = Pattern.compile("\\[[^\\[]*\\]");
        Matcher matcher = pattern.matcher(this.content);

        ArrayList<String> result = new ArrayList<>();

        while (matcher.find()) {
            result.add(matcher.group());
        }

        return result;
    }

}
