package com.madlibs.data;


import com.madlibs.config.ServerConfigs;
import com.madlibs.model.RegisteredUser;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import org.sqlite.SQLiteDataSource;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Database service. Handles SQLite database calls.
 * Created by Ran on 12/20/2015.
 */
public class DatabaseService {

    private Sql2o database;

    private File databaseFile;

    private static DatabaseService instance;

    static {
        instance = new DatabaseService("data/madlibs.db");
        instance.initializeDatabase();
    }

    /**
     * Accessor for singleton instance of database service.
     * @return Singleton instance of database service.
     */
    public static DatabaseService getInstance() {
        return instance;
    }

    /**
     * Initializes a service for the SQLite database contained within the file.
     * @param filename Filename of SQLite database file.
     */
    public DatabaseService(String filename) {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl("jdbc:sqlite:" + filename);
        this.database = new Sql2o(dataSource);
        this.databaseFile = new File(filename);
    }

    /**
     * Returns Sql2o object.
     * @return Sql2o database object.
     */
    public Sql2o getDatabase() {
        return this.database;
    }

    /**
     * Initializes database with all necessary tables.
     */
    public void initializeDatabase() {
        Connection connection = this.database.open();
        initializeServerConfigsTable(connection);
        initializeUsersTable(connection);

        connection.close();
    }

    private void initializeServerConfigsTable(Connection connection) {
        // Create server configuration table
        String serverConfigTableQueryString = "create table if not exists serverConfig(templateId INTEGER, scriptId INTEGER)";
        Query serverConfigTableQuery = connection.createQuery(serverConfigTableQueryString);
        serverConfigTableQuery.executeUpdate();

        // Check if configs are already there.
        String checkForConfigsQueryString = "select * from serverConfig";
        Query checkForConfigsQuery = connection.createQuery(checkForConfigsQueryString);
        List<Map<String, Object>> result = checkForConfigsQuery.executeAndFetchTable().asList();
        // If empty, add in default configs.
        if (result.isEmpty()) {
            String defaultConfigsQueryString = "insert into serverConfig values(:templateId, :scriptId)";
            Query defaultConfigsQuery = connection.createQuery(defaultConfigsQueryString);
            defaultConfigsQuery.addParameter("templateId", 0);
            defaultConfigsQuery.addParameter("scriptId", 0);
            defaultConfigsQuery.executeUpdate();
        }
    }

    private void initializeUsersTable(Connection connection) {
        // Create users table
        String userTableQueryString = "create table if not exists users(username TEXT, saltedHashedPassword TEXT, salt TEXT)";
        Query userTableQuery = connection.createQuery(userTableQueryString);
        userTableQuery.executeUpdate();
    }

    /**
     * Checks if user exists in the database.
     * @param username Username of user to check.
     * @return True if user exists.
     */
    public boolean userExists(String username) {
        Connection connection = this.database.open();

        String queryString = "select * from users where username = :username";
        Query query = connection.createQuery(queryString);
        query.addParameter("username", username);
        List<RegisteredUser> result = query.executeAndFetch(RegisteredUser.class);
        connection.close();

        return !result.isEmpty();
    }

    /**
     * Stores user in database.
     * @param user User to store.
     */
    public void addUser(RegisteredUser user) {
        Connection connection = this.database.open();

        String queryString = "insert into users values(:username, :password, :salt)";
        Query query = connection.createQuery(queryString);
        query.addParameter("username", user.getUsername());
        query.addParameter("password", user.getSaltedHashedPassword());
        query.addParameter("salt", user.getSalt());
        query.executeUpdate();

        connection.close();
    }

    /**
     * Updates user in database.
     * @param user User to update.
     */
    public void updateUser(RegisteredUser user) {
        Connection connection = this.database.open();

        String queryString = "update users set saltedHashedPassword = :password, salt = :salt where username = :username";
        Query query = connection.createQuery(queryString);
        query.addParameter("password", user.getSaltedHashedPassword());
        query.addParameter("salt", user.getSalt());
        query.addParameter("username", user.getUsername());
        query.executeUpdate();

        connection.close();
    }

    /**
     * Gets user from database.
     * @param username Username of user.
     * @return RegisteredUser object, if exists, else null.
     */
    public RegisteredUser getUser(String username) {
        Connection connection = this.database.open();

        String queryString = "select * from users where username = :username";
        Query query = connection.createQuery(queryString);
        query.addParameter("username", username);
        List<RegisteredUser> result = query.executeAndFetch(RegisteredUser.class);
        connection.close();

        if (result.isEmpty()) {
            return null;
        } else {
            return result.get(0);
        }
    }

    /**
     * Retrieves server configurations from database.
     * @return Server configurations.
     */
    public ServerConfigs getServerConfigs() {
        Connection connection = this.database.open();

        String queryString = "select * from serverConfig";
        Query query = connection.createQuery(queryString);
        List<ServerConfigs> configs = query.executeAndFetch(ServerConfigs.class);
        return configs.get(0);
    }

    /**
     * Deletes the database.
     */
    public void delete() {
        this.databaseFile.delete();
    }
}