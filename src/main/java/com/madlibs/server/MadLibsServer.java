package com.madlibs.server;

import com.madlibs.authentication.AuthToken;
import com.madlibs.config.ServerConfigs;
import com.madlibs.data.DatabaseService;
import com.madlibs.model.MadLibsSession;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Mad libs server object.
 * Created by Ran on 12/22/2015.
 */
public class MadLibsServer {

    private ServerConfigs configs;
    private List<MadLibsSession> gameSessions;
    private List<AuthToken> authTokens;
    private static MadLibsServer instance;

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
        this.gameSessions = new ArrayList<>();
        this.authTokens = new ArrayList<>();
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

}
