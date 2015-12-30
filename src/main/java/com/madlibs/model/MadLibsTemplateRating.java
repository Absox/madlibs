package com.madlibs.model;

import com.google.gson.JsonObject;

/**
 * A rating on a template.
 * Created by Ran on 12/30/2015.
 */
public class MadLibsTemplateRating {

    private String user;
    private String templateId;
    private RatingType ratingType;

    /**
     * Constructor for rating.
     * @param user User who is rating.
     * @param templateId Id of template who they are rating.
     * @param value Value of the rating (
     */
    public MadLibsTemplateRating(String user, String templateId, String value) {
        this.user = user;
        this.templateId = templateId;
        this.ratingType = RatingType.getType(value);
    }

    /**
     * Accessor for user.
     * @return User who did this rating.
     */
    public String getUser() {
        return this.user;
    }

    /**
     * Accessor for id of template being rated.
     * @return Id of template being rated.
     */
    public String getTemplateId() {
        return this.templateId;
    }

    /**
     * Accessor for value of rating.
     * @return Value of rating.
     */
    public String getRating() {
        return this.ratingType.getValue();
    }

    /**
     * Returns the json representation of this object.
     * @return JsonObject representation of this.
     */
    public JsonObject getJson() {
        JsonObject result = new JsonObject();
        result.addProperty("user", user);
        result.addProperty("templateId", templateId);
        result.addProperty("rating", ratingType.getValue());
        return result;
    }

    /**
     * Enum for rating.
     */
    private enum RatingType {

        UP("up"), DOWN("down"), NONE("none");

        private String value;

        /**
         * Gets the enum corresponding to the value.
         * @param value Text value.
         * @return Enumerated type, if there is one associated with the text value. Else null.
         */
        private static RatingType getType(String value) {
            value = value.toLowerCase();
            for (RatingType t : RatingType.values()) {
                if (t.getValue().equals(value)) return t;
            }
            return null;
        }

        /**
         * Constructor for a rating enum.
         * @param value String value of enumerated object.
         */
        private RatingType(String value) {
            RatingType.
            this.value = value;
        }

        /**
         * Accessor for value.
         * @return String value of enum name.
         */
        private String getValue() {
            return this.value;
        }
    }
}
