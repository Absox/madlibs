package com.madlibs.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Mad libs game session.
 * Created by Ran on 12/22/2015.
 */
public class MadLibsSession {

    private String host;
    private String id;
    private List<String> participants; // Circular queue of participants
    private int queueHeadPosition;

    private List<ChatMessage> chat;
    private MadLibsTemplate template;

    private List<String> prompts;
    private int currentPromptIndex;

    /**
     * Creates a new mad libs session.
     * @param id Id of session.
     * @param host Host of session.
     * @param template Template being used for session.
     */
    public MadLibsSession(String id, String host, MadLibsTemplate template) {
        this.host = host;
        this.id = id;
        this.participants = new ArrayList<>();
        this.chat = new ArrayList<>();
        this.template = template;
        this.prompts = template.getPrompts();
    }
}
