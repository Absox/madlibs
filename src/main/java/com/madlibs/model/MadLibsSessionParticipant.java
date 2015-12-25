package com.madlibs.model;

import org.eclipse.jetty.websocket.api.Session;

/**
 * Participant in a mad libs session.
 * Created by Ran on 12/25/2015.
 */
public class MadLibsSessionParticipant {

    /**
     * Websocket session associated with this participant.
     */
    private Session session;

    /**
     * Identifier.
     */
    private String identifier;

    /**
     * Constructs a participant.
     * @param identifier Identifier for the participant.
     * @param session Websocket session.
     */
    public MadLibsSessionParticipant(String identifier, Session session) {
        this.identifier = identifier;
        this.session = session;
    }

    /**
     * Accessor for identifier.
     * @return Identifier of participant.
     */
    public String getIdentifier() {
        return this.identifier;
    }

    /**
     * Mutator for websocket session.
     * @param session New websocket session associated.
     */
    public void setSession(Session session) {
        this.session = session;
    }

    /**
     * Accessor for websocket session.
     * @return Websocket session.
     */
    public Session getSession() {
        return this.session;
    }
}
