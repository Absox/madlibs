package com.madlibs.model;

/**
 * A response to a blank in a mad libs template.
 * Created by Ran on 12/22/2015.
 */
public class MadLibsResponse {

    private String user;
    private String value;

    /**
     * Constructs a response.
     * @param user User who submitted responses.
     * @param value Value of response.
     */
    public MadLibsResponse(String user, String value) {
        this.user = user;
        this.value = value;
    }

    /**
     * Accessor for user.
     * @return User who submitted response.
     */
    public String getUser() {
        return this.user;
    }

    /**
     * Accessor for value.
     * @return Value of response.
     */
    public String getValue() {
        return this.value;
    }
}
