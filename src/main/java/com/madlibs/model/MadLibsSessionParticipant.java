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

    /**
     * Checks if this object is equal to another object.
     * @param o Object to compare to.
     * @return True if also instance of participant, with equivalent identifier and session.
     */
    public boolean equals(Object o) {
        if (o instanceof MadLibsSessionParticipant) {
            MadLibsSessionParticipant other = (MadLibsSessionParticipant)o;
            if (this.identifier.equals(other.identifier) && this.session.equals(other.session)) return true;
        }

        return false;
    }
}
