package com.madlibs.model;


/**
 * Chat message.
 * Created by Ran on 12/22/2015.
 */
public class ChatMessage {

    private long dateStamp;
    private String user;
    private String value;

    /**
     * Creates a chat message.
     * @param dateStamp Date stamp of message.
     * @param user User who sent message.
     * @param value Value of message.
     */
    public ChatMessage(long dateStamp, String user, String value) {
        this.dateStamp = dateStamp;
        this.user = user;
        this.value = value;
    }

    /**
     * Accessor for datestamp.
     * @return Date stamp.
     */
    public long getDateStamp() {
        return this.dateStamp;
    }

    /**
     * Accessor for user.
     * @return User.
     */
    public String getUser() {
        return this.user;
    }

    /**
     * Accessor for value.
     * @return Value of chat message.
     */
    public String getValue() {
        return this.value;
    }

}
