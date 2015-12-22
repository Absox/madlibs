package com.madlibs.data;


import org.sql2o.Sql2o;
import org.sqlite.SQLiteDataSource;

/**
 * Database service. Handles SQLite database calls.
 * Created by Ran on 12/20/2015.
 */
public class DatabaseService {

    private Sql2o database;

    /**
     * Initializes a service for the SQLite database contained within the file.
     * @param filename Filename of SQLite database file.
     */
    public DatabaseService(String filename) {

        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl("jdbc:sqlite:" + filename);
        this.database = new Sql2o(dataSource);
    }

    /**
     * Returns Sql2o object.
     * @return Sql2o database object.
     */
    public Sql2o getDatabase() {
        return this.database;
    }

    public boolean userExists(String username) {
        return true; // TODO
    }
}
