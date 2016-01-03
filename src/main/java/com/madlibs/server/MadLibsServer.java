package com.madlibs.server;

import com.madlibs.authentication.AuthToken;
import com.madlibs.config.ServerConfigs;
import com.madlibs.data.DatabaseService;
import com.madlibs.model.MadLibsScript;
import com.madlibs.model.MadLibsSession;
import com.madlibs.model.MadLibsTemplate;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.util.*;

/**
 * Mad libs server object.
 * Created by Ran on 12/22/2015.
 */
public class MadLibsServer {

    private static MadLibsServer instance;

    private ServerConfigs configs;
    private List<AuthToken> authTokens;
    private HashMap<String, MadLibsSession> gameSessions;
    private HashMap<Session, MadLibsSession> sessionMap;

    static {
        instance = new MadLibsServer(DatabaseService.getInstance().getServerConfigs());
    }

    /**
     * Accessor for singleton instance.
     * @return Singleton instance of server.
     */
    public static MadLibsServer getInstance() {
        return instance;
    }

    /**
     * Creates a new server for handling games.
     */
    public MadLibsServer() {
        this.authTokens = new ArrayList<>();
        this.gameSessions = new HashMap<>();
        this.sessionMap = new HashMap<>();
        this.configs = new ServerConfigs(0, 0, 0);
    }

    /**
     * Initializes a new server with configs.
     * @param configs Configs.
     */
    public MadLibsServer(ServerConfigs configs) {
        this();
        this.configs = configs;
    }

    /**
     * Issues an auth token for a user.
     * @param username Username of user.
     * @return Auth token.
     */
    public AuthToken issueToken(String username) {
        AuthToken result = new AuthToken(username);
        this.authTokens.add(result);
        return result;
    }

    /**
     * Authenticates a user.
     * @param token Auth token.
     * @return New authtoken, otherwise null if invalid.
     */
    public AuthToken authenticate(AuthToken token) {

        Iterator<AuthToken> iterator = this.authTokens.iterator();

        while (iterator.hasNext()) {

            AuthToken currentToken = iterator.next();
            // Remove all expired tokens.
            if (currentToken.getExpiration() < new Date().getTime()) {
                iterator.remove();
                continue;
            }

            // Check if matches username.
            if (currentToken.getUsername().equals(token.getUsername()) & currentToken.getValue().equals(token.getValue())) {
                iterator.remove();
                AuthToken newToken = new AuthToken(token.getUsername());
                this.authTokens.add(newToken);
                return newToken;
            }

        }
        return null;
    }

    /**
     * Accessor for configs.
     * @return Configurations of this server.
     */
    public ServerConfigs getConfigs() {
        return this.configs;
    }

    /**
     * Mutator for configs.
     * @param configs New configs object for server.
     */
    public void setConfigs(ServerConfigs configs) {
        this.configs = configs;
    }

    /**
     * Creates a game session.
     * @param id Id of game session.
     * @param host Host username.
     * @param template Template being used.
     */
    public void createSession(String id, String host, MadLibsTemplate template) {
        MadLibsSession newSession = new MadLibsSession(id, host, template);
        this.gameSessions.put(id, newSession);
    }

    /**
     * Alternate create method, takes integer parameters.
     * @param id Id of game session.
     * @param host Host username.
     * @param template Template.
     */
    public void createSession(int id, String host, MadLibsTemplate template) {
        String hexId = Integer.toHexString(id);
        MadLibsSession newSession = new MadLibsSession(hexId, host, template);
        this.gameSessions.put(hexId, newSession);
    }

    /**
     * Gets the game session with corresponding id.
     * @param id Id of game session.
     * @return MadLibsSession if there exists one; else null.
     */
    public MadLibsSession getSessionById(String id) {
        if (this.gameSessions.containsKey(id)) {
            return this.gameSessions.get(id);
        }
        return null;
    }

    /**
     * Gets the game session associated with the corresponding websocket session.
     * @param session Websocket session.
     * @return MadLibsSession if there exists one; else null.
     */
    public MadLibsSession getSessionBySession(Session session) {
        if (this.sessionMap.containsKey(session)) {
            return this.sessionMap.get(session);
        }
        return null;
    }

    /**
     * Adds participant to session.
     * @param id Id of session.
     * @param identifier Identifier, if the user is logged in. Else, null.
     * @param session Websocket session.
     */
    public void addParticipantToSession(String id, Session session, String identifier) {
        MadLibsSession gameSession = this.getSessionById(id);
        gameSession.participantJoin(identifier, session);
        this.sessionMap.put(session, gameSession);
    }

    /**
     * Disconnects a participant.
     * @param session Websocket session.
     */
    public void disconnectParticipant(Session session) {
        MadLibsSession gameSession = this.sessionMap.get(session);
        gameSession.participantLeave(session);
        this.sessionMap.remove(session);

        // Last participant left
        if (gameSession.getNumParticipants() == 0) {
            System.out.println("Last participant left: session " + gameSession.getId() + " removed!");
            this.gameSessions.remove(gameSession.getId());
        }
    }

    /**
     * Ends a game session, stores to db.
     * @param id Id of game session.
     * @return Script created by finalizing session.
     */
    public MadLibsScript finalizeSession(String id) throws IOException {

        MadLibsSession session = this.gameSessions.get(id);
        MadLibsScript script = new MadLibsScript(session);
        DatabaseService.getInstance().addScript(script);

        return script;
    }

}
