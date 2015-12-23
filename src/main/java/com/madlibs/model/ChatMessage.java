package com.madlibs.model;


/**
 * Chat message.
 * Created by Ran on 12/22/2015.
 */
public class ChatMessage {

    private String dateStamp;
    private String user;
    private String value;

    /**
     * Creates a chat message.
     * @param dateStamp Date stamp of message.
     * @param user User who sent message.
     * @param value Value of message.
     */
    public ChatMessage(String dateStamp, String user, String value) {
        this.dateStamp = dateStamp;
        this.user = user;
        this.value = value;
    }

}
