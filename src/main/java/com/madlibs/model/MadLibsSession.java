package com.madlibs.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ran on 12/22/2015.
 */
public class MadLibsSession {

    private String host;
    private String id;
    private MadLibsScript script;
    private List<String> participants;
    private List<ChatMessage> chat;

    /**
     * Creates a new mad libs session.
     * @param id Id of session.
     * @param host Host of session.
     * @param template Template being used for session.
     */
    public MadLibsSession(String id, String host, MadLibsTemplate template) {
        this.host = host;
        this.id = id;
        this.script = new MadLibsScript(template, host, id);
        this.participants = new ArrayList<>();
        this.chat = new ArrayList<>();
    }
}
