package com.madlibs.model;

import org.eclipse.jetty.websocket.api.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Mad libs game session.
 * Created by Ran on 12/22/2015.
 */
public class MadLibsSession {

    private String host;
    private String id;
    private List<MadLibsSessionParticipant> participants; // Circular queue of participants
    private int queueHeadPosition;

    private List<ChatMessage> chat;
    private MadLibsTemplate template;

    private List<String> prompts;
    private List<MadLibsResponse> responses;
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
        this.responses = new ArrayList<>();
    }

    /**
     * Accessor for id.
     * @return Id of session.
     */
    public String getId() {
        return this.id;
    }

    /**
     * Gets host of session.
     * @return Username of host.
     */
    public String getHost() {
        return this.host;
    }

    /**
     * Accessor for template content.
     * @return Template content.
     */
    public String getTemplateContent() {
        return this.template.getContent();
    }

    /**
     * Accessor for chat log.
     * @return Chat log, as list of messages.
     */
    public List<ChatMessage> getChatLog() {
        return this.chat;
    }

    /**
     * Accessor for list of responses.
     * @return Gets list of responses.
     */
    public List<MadLibsResponse> getResponses() {
        return this.responses;
    }

    /**
     * Returns the current prompt, if the game has not ended.
     * @return Current prompt, else null.
     */
    public String getCurrentPrompt() {
        if (currentPromptIndex < prompts.size()) {
            return prompts.get(currentPromptIndex);
        }
        return null;
    }

    /**
     * Adds a participant to the session.
     * @param identifier Identifier of the participant.
     * @param session Websocket session.
     */
    public void participantJoin(String identifier, Session session) {
        this.participants.add(new MadLibsSessionParticipant(identifier, session));
    }

    /**
     * Binds websocket session to participant.
     * @param identifier Identifier of participant.
     * @param session Websocket session to bind.
     * @return True if success, false if failure (not found).
     */
    public boolean bindSessionToParticipant(String identifier, Session session) {
        MadLibsSessionParticipant participant = this.getParticipantByIdentifier(identifier);
        if (participant != null) {
            participant.setSession(session);
            return true;
        }
        return false;
    }

    /**
     * Gets participant object by identifier of participant.
     * @param identifier Identifier of participant.
     * @return MadLibsSessionParticipant object if one exists, else null.
     */
    public MadLibsSessionParticipant getParticipantByIdentifier(String identifier) {
        for (MadLibsSessionParticipant p : participants) {
            if (p.getIdentifier().equals(identifier)) return p;
        }
        return null;
    }

    /**
     * Gets participant object by the websocket session bound to them.
     * @param session Websocket session bound to participant.
     * @return MadLibsSessionParticipant if match found, else null.
     */
    public MadLibsSessionParticipant getParticipantBySession(Session session) {
        for (MadLibsSessionParticipant p : participants) {
            if (p.getSession().equals(session)) return p;
        }
        return null;
    }
}
