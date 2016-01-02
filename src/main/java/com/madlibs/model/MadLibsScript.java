package com.madlibs.model;

import java.util.List;

/**
 * Object representation of a script (completed template)
 * Created by Ran on 1/2/2016.
 */
public class MadLibsScript {

    private String id;
    private String user;
    private String template;
    private List<MadLibsResponse> responses;
    private List<ChatMessage> chatlog;

    /**
     * Creates a script from a session.
     * @param session Game session.
     */
    public MadLibsScript(MadLibsSession session) {
        this.id = session.getId();
        this.user = session.getHost();
        this.template = session.getTemplateContent();
        this.responses = session.getResponses();
        this.chatlog = session.getChatLog();
    }
}
