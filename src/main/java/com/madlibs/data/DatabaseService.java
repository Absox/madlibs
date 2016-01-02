package com.madlibs.data;


import com.google.gson.Gson;
import com.madlibs.config.ServerConfigs;
import com.madlibs.model.*;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import org.sqlite.SQLiteDataSource;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

// TODO refactor into DAO pattern

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
        initializeTemplateTable(connection);
        initializeTemplateCommentsTable(connection);
        initializeTemplateRatingsTable(connection);
        initializeScriptTable(connection);

        connection.close();
    }

    /**
     * Initializes table of scripts.
     * @param connection
     */
    private void initializeScriptTable(Connection connection) {
        String scriptTableQueryString = "create table if not exists scripts(id TEXT, user TEXT, template TEXT, responses TEXT, chatlog TEXT";
        Query scriptTableQuery = connection.createQuery(scriptTableQueryString);
        scriptTableQuery.executeUpdate();
    }

    /**
     * Initializes table of template ratings.
     * @param connection
     */
    private void initializeTemplateRatingsTable(Connection connection) {
        String templateRatingTableQueryString = "create table if not exists templateRatings(templateId TEXT, user TEXT, value TEXT)";
        Query templateRatingTableQuery = connection.createQuery(templateRatingTableQueryString);
        templateRatingTableQuery.executeUpdate();
    }

    /**
     * Initializes template comments table.
     * @param connection Database connection.
     */
    private void initializeTemplateCommentsTable(Connection connection) {
        String templateCommentsTableQueryString = "create table if not exists templateComments(templateId TEXT, user TEXT, value TEXT, date INTEGER)";
        Query templateCommentsTableQuery = connection.createQuery(templateCommentsTableQueryString);
        templateCommentsTableQuery.executeUpdate();
    }

    /**
     * Initializes the template table.
     * @param connection Database connection.
     */
    private void initializeTemplateTable(Connection connection) {
        String templateTableQueryString = "create table if not exists templates(id TEXT, title TEXT, creator TEXT, rating INTEGER, content TEXT)";
        Query templateTableQuery = connection.createQuery(templateTableQueryString);
        templateTableQuery.executeUpdate();
    }

    /**
     * Initializes the server configs table.
     * @param connection Database connection.
     */
    private void initializeServerConfigsTable(Connection connection) {
        // Create server configuration table
        String serverConfigTableQueryString = "create table if not exists serverConfig(templateId INTEGER, scriptId INTEGER, commentId INTEGER)";
        Query serverConfigTableQuery = connection.createQuery(serverConfigTableQueryString);
        serverConfigTableQuery.executeUpdate();

        // Check if configs are already there.
        String checkForConfigsQueryString = "select * from serverConfig";
        Query checkForConfigsQuery = connection.createQuery(checkForConfigsQueryString);
        List<Map<String, Object>> result = checkForConfigsQuery.executeAndFetchTable().asList();
        // If empty, add in default configs.
        if (result.isEmpty()) {
            String defaultConfigsQueryString = "insert into serverConfig values(:templateId, :scriptId, :commentId)";
            Query defaultConfigsQuery = connection.createQuery(defaultConfigsQueryString);
            defaultConfigsQuery.addParameter("templateId", 0);
            defaultConfigsQuery.addParameter("scriptId", 0);
            defaultConfigsQuery.addParameter("commentId", 0);
            defaultConfigsQuery.executeUpdate();
        }
    }

    /**
     * Initializes the users table.
     * @param connection Database connection.
     */
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

        connection.close();

        return configs.get(0);
    }

    /**
     * Updates server configurations.
     * @param configs Server configurations.
     */
    public void updateServerConfigs(ServerConfigs configs) {

        Connection connection = this.database.open();

        // Clear current configs
        String queryString = "delete from serverConfig";
        Query query = connection.createQuery(queryString);
        query.executeUpdate();

        // Insert new ones.
        String insertQueryString = "insert into serverConfig values(:templateId, :scriptId, :commentId)";
        Query insertQuery = connection.createQuery(insertQueryString);
        insertQuery.addParameter("templateId", configs.getTemplateId());
        insertQuery.addParameter("scriptId", configs.getScriptId());
        insertQuery.addParameter("commentId", configs.getCommentId());
        insertQuery.executeUpdate();

        connection.close();
    }

    /**
     * Adds template to database.
     * @param template Template to add to database.
     */
    public void addTemplate(MadLibsTemplate template) {
        Connection connection = this.database.open();

        String queryString = "insert into templates values(:id, :title, :creator, :rating, :content)";
        Query query = connection.createQuery(queryString);
        query.addParameter("id", template.getId());
        query.addParameter("title", template.getTitle());
        query.addParameter("creator", template.getCreator());
        query.addParameter("rating", template.getRating());
        query.addParameter("content", template.getContent());
        query.executeUpdate();

        connection.close();
    }

    /**
     * Checks if a template exists.
     * @param id Id of template.
     * @return True if exists, else false.
     */
    public boolean templateExists(String id) {
        Connection connection = this.database.open();

        String queryString = "select * from templates where id = :id";
        Query query = connection.createQuery(queryString);
        query.addParameter("id", id);
        List<MadLibsTemplate> results = query.executeAndFetch(MadLibsTemplate.class);

        connection.close();
        return !results.isEmpty();
    }

    /**
     * Gets template from database.
     * @param id Id of template.
     * @return MadLibsTemplate object if it exists, else null.
     */
    public MadLibsTemplate getTemplate(String id) {
        Connection connection = this.database.open();

        String queryString = "select * from templates where id = :id";
        Query query = connection.createQuery(queryString);
        query.addParameter("id", id);
        List<MadLibsTemplate> results = query.executeAndFetch(MadLibsTemplate.class);

        connection.close();

        if (results.isEmpty()) {
            return null;
        } else {
            return results.get(0);
        }
    }

    /**
     * Adds a comment to a template.
     * @param comment Comment to add.
     */
    public void addTemplateComment(MadLibsTemplateComment comment) {
        Connection connection = this.database.open();

        String queryString = "insert into templateComments values(:templateId, :user, :content, :date)";
        Query query = connection.createQuery(queryString);
        query.addParameter("templateId", comment.getTemplateId());
        query.addParameter("user", comment.getUser());
        query.addParameter("content", comment.getValue());
        query.addParameter("date", comment.getDate());
        query.executeUpdate();

        connection.close();
    }

    /**
     * Retrieves a list of comments for a template.
     * @param templateId Id of template to get comments for.
     * @return List of comments.
     */
    public List<MadLibsTemplateComment> getCommentsOnTemplate(String templateId) {
        Connection connection = this.database.open();

        String queryString = "select * from templateComments where templateId = :templateId";
        Query query = connection.createQuery(queryString);
        query.addParameter("templateId", templateId);
        List<MadLibsTemplateComment> result = query.executeAndFetch(MadLibsTemplateComment.class);
        connection.close();

        return result;
    }

    /**
     * Updates template.
     * @param template Template object to update in the database.
     */
    public void updateTemplate(MadLibsTemplate template) {
        Connection connection = this.database.open();

        String queryString = "update templates set title = :title, content = :content, rating = :rating where id = :id";
        Query query = connection.createQuery(queryString);
        query.addParameter("title", template.getTitle());
        query.addParameter("content", template.getContent());
        query.addParameter("rating", template.getRating());
        query.addParameter("id", template.getId());
        query.executeUpdate();

        connection.close();
    }

    /**
     * Deletes a template.
     * @param id Template object to delete.
     */
    public void deleteTemplate(String id) {
        Connection connection = this.database.open();

        String queryString = "delete from templates where id = :id";
        Query query = connection.createQuery(queryString);
        query.addParameter("id", id);
        query.executeUpdate();

        connection.close();
    }

    /**
     * Gets the list of templates created by user.
     * @param username Username of creator.
     * @return List of templates.
     */
    public List<MadLibsTemplate> getListOfTemplatesForUser(String username) {
        Connection connection = this.database.open();

        String queryString = "select * from templates where creator = :username";
        Query query = connection.createQuery(queryString);
        query.addParameter("username", username);
        List<MadLibsTemplate> result = query.executeAndFetch(MadLibsTemplate.class);
        connection.close();

        return result;
    }

    /**
     * Updates the rating.
     * @param rating Rating object to update.
     */
    public void updateRating(MadLibsTemplateRating rating) {
        Connection connection = this.database.open();

        // Check if an entry already exists, if so, update it.
        if (this.getRating(rating.getUser(), rating.getTemplateId()) != null) {
            String updateQueryString = "update templateRatings set value = :value where user = :user and templateId = :templateId";
            Query updateQuery = connection.createQuery(updateQueryString);
            updateQuery.addParameter("value", rating.getRating());
            updateQuery.addParameter("user", rating.getUser());
            updateQuery.addParameter("templateId", rating.getTemplateId());
            updateQuery.executeUpdate();

        // Else we make a new entry
        } else {
            String newEntryQueryString = "insert into templateRatings values(:templateId, :user, :value)";
            Query newEntryQuery = connection.createQuery(newEntryQueryString);
            newEntryQuery.addParameter("templateId", rating.getTemplateId());
            newEntryQuery.addParameter("user", rating.getUser());
            newEntryQuery.addParameter("value", rating.getRating());
            newEntryQuery.executeUpdate();
        }

        connection.close();
    }

    /**
     * Gets a rating object from database.
     * @param username User who's rating.
     * @param templateId Template being rated.
     * @return Rating object.
     */
    public MadLibsTemplateRating getRating(String username, String templateId) {
        Connection connection = database.open();
        String queryString = "select * from templateRatings where user = :user and templateId = :templateId";
        Query query = connection.createQuery(queryString);
        query.addParameter("user", username);
        query.addParameter("templateId", templateId);
        List<Map<String, Object>> results = query.executeAndFetchTable().asList();
        connection.close();

        if (!results.isEmpty()) {
            Map<String, Object> currentResult = results.get(0);
            return new MadLibsTemplateRating((String)currentResult.get("user"), (String)currentResult.get("templateid"), (String)currentResult.get("value"));
        }
        return null;
    }

    /**
     * Adds a script to the database.
     * @param script Script to add
     */
    public void addScript(MadLibsScript script) {
        Connection connection = database.open();

        String sql = "insert into scripts values(:id, :user, :template, :responses, :chatlog)";
        Query query = connection.createQuery(sql);
        query.addParameter("id", script.getId());
        query.addParameter("user", script.getUser());
        query.addParameter("template", script.getTemplateContent());
        query.addParameter("responses", script.getResponsesJson());
        query.addParameter("chatlog", script.getChatlogJson());
        query.executeUpdate();

        connection.close();
    }

    /**
     * Retrieves a script from the database.
     * @param id Id of script.
     * @return Script object retrieved from database.
     */
    public MadLibsScript getScript(String id) {
        Connection connection = database.open();

        String sql = "select * from scripts where id = :id";
        Query query = connection.createQuery(sql);
        List<Map<String, Object>> results = query.executeAndFetchTable().asList();
        connection.close();

        if (results.isEmpty()) return null;
        Map<String, Object> result = results.get(0);

        Gson gson = new Gson();

        String user = (String)result.get("user");
        String template = (String)result.get("template");
        List<MadLibsResponse> responses = Arrays.asList(gson.fromJson((String)result.get("responses"), MadLibsResponse[].class));
        List<ChatMessage> chatlog = Arrays.asList(gson.fromJson((String)result.get("chatlog"), ChatMessage[].class));

        MadLibsScript retrievedScript = new MadLibsScript(id, user, template, responses, chatlog);

        return retrievedScript;
    }

    /**
     * Deletes the database.
     */
    public void delete() {
        this.databaseFile.delete();
    }
}