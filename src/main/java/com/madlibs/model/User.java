package com.madlibs.model;

/**
 * User class.
 * Created by Ran on 12/20/2015.
 */
public class User {

    private String username;

    /**
     * Construct a user with username.
     * @param username Username of user.
     */
    public User(String username) {
        this.username = username;
    }

    /**
     * Accessor for username.
     * @return Username of user.
     */
    public String getUsername() {
        return this.username;
    }

}
