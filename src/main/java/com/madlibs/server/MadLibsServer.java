package com.madlibs.server;

import com.madlibs.config.ServerConfigs;
import com.madlibs.data.DatabaseService;
import com.madlibs.model.MadLibsSession;

import java.util.ArrayList;
import java.util.List;

/**
 * Mad libs server object.
 * Created by Ran on 12/22/2015.
 */
public class MadLibsServer {

    private ServerConfigs configs;
    private List<MadLibsSession> gameSessions;
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
        this.configs = new ServerConfigs(0, 0, 0);
    }

    /**
     * Initializes a new server with configs.
     * @param configs Configs.
     */
    public MadLibsServer(ServerConfigs configs) {
        this.gameSessions = new ArrayList<>();
        this.configs = configs;
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
