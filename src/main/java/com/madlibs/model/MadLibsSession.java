package com.madlibs.model;

import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
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
     * Alternate constructor, takes integer id.
     * @param id Id of session.
     * @param host Host of session.
     * @param template Template being used for session.
     */
    public MadLibsSession(int id, String host, MadLibsTemplate template) {
        this(Integer.toHexString(id), host, template);
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
     * Checks if session is finished.
     * @return True if session is finished.
     */
    public boolean isFinished() {
        return currentPromptIndex >= prompts.size();
    }

    /**
     * Adds message to chat log.
     * @param message Message to add to chatlog.
     */
    public void addChatMessage(ChatMessage message) {
        this.chat.add(message);
    }

    /**
     * Gets the number of participants in the session.
     * @return Number of participants in the session.
     */
    public int getNumParticipants() {
        return this.participants.size();
    }

    /**
     * Accessor for participants.
     * @return Participants in the session.
     */
    public List<MadLibsSessionParticipant> getParticipants() {
        return this.participants;
    }

    /**
     * Accessor for the identifiers of the participants.
     * @return List of identifiers
     */
    public List<String> getParticipantIdentifiers() {
        List<String> result = new ArrayList<>();

        for (MadLibsSessionParticipant p : participants) {
            result.add(p.getIdentifier());
        }

        return result;
    }

    /**
     * Gets the participant whose turn it is.
     * @return Participant whose turn it is
     */
    public MadLibsSessionParticipant getCurrentParticipant() {
        if (queueHeadPosition < participants.size()) {
            return participants.get(queueHeadPosition);
        }
        return null;
    }

    /**
     * Gets the next participant.
     * @return Participant whose turn it is next.
     */
    public MadLibsSessionParticipant getNextParticipant() {
        if (participants.size() == 0) return null;
        if (queueHeadPosition + 1 >= participants.size()) {
            return participants.get(0);
        }

        return participants.get(queueHeadPosition + 1);
    }

    /**
     * Adds a response.
     * @param value Value of response.
     */
    public void addResponse(String value) {
        MadLibsSessionParticipant currentParticipant = this.getCurrentParticipant();
        this.responses.add(new MadLibsResponse(currentParticipant.getIdentifier(), value));
        this.currentPromptIndex++;
        this.queueHeadPosition++;
        if (this.queueHeadPosition >= this.participants.size()) {
            this.queueHeadPosition = 0;
        }
    }

    /**
     * Adds a participant to the session.
     * @param identifier Identifier of the participant.
     * @param session Websocket session.
     */
    public void participantJoin(String identifier, Session session) {
        System.out.println("Session " + this.getId() + " : participant " + identifier + " joined!");

        this.participants.add(new MadLibsSessionParticipant(identifier, session));
    }

    /**
     * Removes a participant from session.
     * @param identifier Identifier of participant
     * @return True if found, else false.
     */
    public boolean participantLeave(String identifier) {
        return this.removeParticipant((participant) -> participant.getIdentifier().equals(identifier));
    }

    /**
     * Removes a participant from session.
     * @param session Websocket session associated with participant.
     * @return True if found, else false.
     */
    public boolean participantLeave(Session session) {
        return this.removeParticipant((participant) -> participant.getSession().equals(session));
    }

    /**
     * Removes a participant from the session.
     * @param criterion Functional interface containing the removal criterion.
     * @return True if removed, else false.
     */
    private boolean removeParticipant(SelectionCriterion criterion) {
        Iterator<MadLibsSessionParticipant> iterator = this.participants.iterator();
        while (iterator.hasNext()) {
            MadLibsSessionParticipant currentParticipant = iterator.next();
            if (criterion.shouldSelect(currentParticipant)) {

                System.out.println("Session " + this.getId() + " : participant " + currentParticipant.getIdentifier() + " removed!");

                iterator.remove();
                if (queueHeadPosition >= this.participants.size()) {
                    queueHeadPosition = 0;
                }
                return true;
            }
        }
        return false;
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
        return this.getParticipant((participant) -> participant.getIdentifier().equals(identifier));
    }

    /**
     * Gets participant object by the websocket session bound to them.
     * @param session Websocket session bound to participant.
     * @return MadLibsSessionParticipant if match found, else null.
     */
    public MadLibsSessionParticipant getParticipantBySession(Session session) {
        return this.getParticipant((participant) -> participant.getSession().equals(session));
    }

    /**
     * Gets a participant based on the criterion passed.
     * @param criterion Criterion for selecting a participant.
     * @return Participant if match found, else null.
     */
    private MadLibsSessionParticipant getParticipant(SelectionCriterion criterion) {
        for (MadLibsSessionParticipant p : participants) {
            if (criterion.shouldSelect(p)) return p;
        }
        return null;
    }

    /**
     * Sends a message to all participants via websocket.
     * @param message Message to send.
     */
    public void sendMessageToAllParticipants(String message) throws IOException {
        for (MadLibsSessionParticipant p : participants) {
            Session session = p.getSession();
            if (session.isOpen()) {
                session.getRemote().sendString(message);
            }
        }
    }

    /**
     * Functional interface containing the criterion for a participant's selection.
     */
    private interface SelectionCriterion {
        boolean shouldSelect(MadLibsSessionParticipant p);
    }
}
