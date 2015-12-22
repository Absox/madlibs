package com.madlibs.data;


import com.madlibs.server.RegisteredUser;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import org.sqlite.SQLiteDataSource;

import java.util.List;

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

    /**
     * Initializes database with all necessary tables.
     */
    public void initializeDatabase() {
        Connection connection = this.database.open();

        String queryString = "create table if not exists users(username TEXT, saltedHashedPassword TEXT, salt TEXT)";
        Query query = connection.createQuery(queryString);
        query.executeUpdate();

        connection.close();
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
    public void storeUser(RegisteredUser user) {
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
}
