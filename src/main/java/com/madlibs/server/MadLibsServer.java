package com.madlibs.server;

import com.madlibs.model.MadLibsSession;

import java.util.ArrayList;
import java.util.List;

/**
 * Mad libs server object.
 * Created by Ran on 12/22/2015.
 */
public class MadLibsServer {

    private List<MadLibsSession> gameSessions;

    private static MadLibsServer instance = new MadLibsServer();

    public static MadLibsServer getInstance() {
        return instance;
    }

    /**
     * Creates a new server for handling games.
     */
    public MadLibsServer() {
        this.gameSessions = new ArrayList<>();
    }
}
