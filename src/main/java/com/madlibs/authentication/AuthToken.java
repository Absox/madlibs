package com.madlibs.authentication;

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

}
