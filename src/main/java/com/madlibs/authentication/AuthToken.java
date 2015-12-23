package com.madlibs.authentication;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.apache.commons.codec.binary.Hex;

import java.security.SecureRandom;
import java.util.Date;

/**
 * Class responsible for managing and generating authentication tokens.
 * Created by Ran on 12/23/2015.
 */
public class AuthToken {

    private String username;
    private String value;
    private long expiration;

    /**
     * Constructs a new authentication token.
     * @param username Username for whom to generate token.
     */
    public AuthToken(String username) {
        this.username = username;

        SecureRandom rng = new SecureRandom();
        byte[] bytes = new byte[16];
        rng.nextBytes(bytes);

        this.value = Hex.encodeHexString(bytes);

        // Expires one day from now
        this.expiration = new Date().getTime() + (long)86400000L;
    }

    /**
     * Returns json string.
     * @return Json string.
     */
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    /**
     * Returns json element.
     * @return Json element.
     */
    public JsonElement toJsonElement() {
        Gson gson = new Gson();
        return gson.toJsonTree(this);
    }

    /**
     * Constructs authtoken from json.
     * @param json Json authtoken.
     * @return AuthToken object.
     */
    public static AuthToken fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, AuthToken.class);
    }

    /**
     * Accessor for username.
     * @return Username.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Accessor for value.
     * @return Value.
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Accessor for expiration time.
     * @return Expiration time.
     */
    public long getExpiration() {
        return this.expiration;
    }

    /**
     * Checks equality with another object.
     * @param o Object we're checking equality with.
     * @return True if equal, otherwise false.
     */
    public boolean equals(Object o) {
        if (o instanceof AuthToken) {

            AuthToken other = (AuthToken)o;
            if (this.username.equals(other.username) && this.value.equals(other.value) && this.expiration == other.expiration) {
                return true;
            }
        }

        return false;
    }


}
