package com.madlibs.authentication;

import java.util.Date;

/**
 * Boilerplate code for manipulating cookies via http headers.
 * Created by Ran on 12/24/2015.
 */
public class Cookie {

    private String name;
    private String value;
    private Date expires;
    private String domain;
    private String path;

    /**
     * Constructs a cookie.
     * @param name
     * @param value
     * @param expires
     * @param domain
     * @param path
     */
    public Cookie(String name, String value, Date expires, String domain, String path) {
        this.name = name;
        this.value = value;
        this.expires = expires;
        this.domain = domain;
        this.path = path;
    }

    /**
     * Gets the value of the Set-Cookie header.
     * @return Set-Cookie header value.
     */
    public String getHeaderValue() {
        StringBuilder result = new StringBuilder();

        return result.toString();
    }
}
