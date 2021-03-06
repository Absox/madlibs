package unit;

import com.madlibs.config.ServerConfigs;
import com.madlibs.data.DatabaseService;
import com.madlibs.model.MadLibsTemplate;
import com.madlibs.model.MadLibsTemplateComment;
import com.madlibs.model.MadLibsTemplateRating;
import com.madlibs.model.RegisteredUser;
import org.apache.commons.codec.DecoderException;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Unit tests for DatabaseService class.
 * Created by Ran on 12/20/2015.
 */
public class DatabaseServiceTest {

    @Test
    public void basicFunctionalityTest() {
        File testFile = new File("test.db");
        if (testFile.exists()) testFile.delete();

        DatabaseService testService = new DatabaseService("test.db");
        Sql2o testDatabase = testService.getDatabase();

        String query = "CREATE TABLE IF NOT EXISTS users(username TEXT, password TEXT)";
        String query2 = "INSERT INTO users values(:username, :password)";
        Connection connection = testDatabase.open();
        connection.createQuery(query).executeUpdate();
        Query insertQuery = connection.createQuery(query2);
        insertQuery.addParameter("username", "absox");
        insertQuery.addParameter("password", "testPassword");
        insertQuery.executeUpdate();

        String selectQuery = "SELECT * FROM users WHERE username = 'absox'";
        List<Map<String, Object>> results = connection.createQuery(selectQuery).executeAndFetchTable().asList();
        assertEquals(results.size(), 1);

        Map<String, Object> currentResult = results.get(0);
        assertEquals(currentResult.get("username"), "absox");
        assertEquals(currentResult.get("password"), "testPassword");

        connection.close();

        testFile.delete();
    }

    @Test
    public void registeredUserTest() {

        File testFile = new File("registeredUserTest.db");
        if (testFile.exists()) testFile.delete();

        DatabaseService testDatabase = new DatabaseService("registeredUserTest.db");
        testDatabase.initializeDatabase();

        try {
            RegisteredUser testUser = new RegisteredUser("absox", "topspinrules");
            assertFalse(testDatabase.userExists("absox"));
            testDatabase.addUser(testUser);
            assertTrue(testDatabase.userExists("absox"));
            RegisteredUser retrievedUser = testDatabase.getUser("absox");
            assertTrue(retrievedUser.validatePassword("topspinrules"));
            testUser.setPassword("emergencycode400iamapotato");
            testDatabase.updateUser(testUser);
            assertTrue(testDatabase.getUser("absox").validatePassword("emergencycode400iamapotato"));

        } catch (IOException e) {

        } catch (DecoderException e) {

        }

        new File("registeredUserTest.db").delete();
    }

    @Test
    public void serverConfigsTest() {

        File testDb = new File("serverConfigTest.db");
        if (testDb.exists()) testDb.delete();

        DatabaseService testDatabase = new DatabaseService("serverConfigTest.db");
        testDatabase.initializeDatabase();

        ServerConfigs testConfigs = testDatabase.getServerConfigs();
        assertEquals(testConfigs.getScriptId(),0);
        assertEquals(testConfigs.getTemplateId(), 0);
        assertEquals(testConfigs.getCommentId(), 0);

        testConfigs.getNextScriptId();
        testConfigs.getNextTemplateId();
        testConfigs.getNextCommentId();
        testDatabase.updateServerConfigs(testConfigs);

        ServerConfigs retrievedConfigs = testDatabase.getServerConfigs();
        assertEquals(retrievedConfigs.getScriptId(), 1);
        assertEquals(retrievedConfigs.getTemplateId(), 1);
        assertEquals(retrievedConfigs.getCommentId(), 1);

        testDb.delete();
    }

    @Test
    public void addUpdateTemplateTest() {

        File testDb = new File("templateAddTest.db");
        if (testDb.exists()) testDb.delete();

        DatabaseService testDatabase = new DatabaseService("templateAddTest.db");
        testDatabase.initializeDatabase();

        MadLibsTemplate testTemplate = new MadLibsTemplate("1f", "Potato Template", "absox", 0, "I am a [adjective] potato");
        assertFalse(testDatabase.templateExists("1f"));
        testDatabase.addTemplate(testTemplate);
        assertTrue(testDatabase.templateExists("1f"));

        MadLibsTemplate retrievedTemplate = testDatabase.getTemplate("1f");
        assertEquals(retrievedTemplate.getNumBlanks(), 1);

        retrievedTemplate.setTitle("Not a potato template");
        retrievedTemplate.setContent("I am no longer a potato");
        testDatabase.updateTemplate(retrievedTemplate);

        MadLibsTemplate reRetrievedTemplate = testDatabase.getTemplate("1f");
        assertEquals(reRetrievedTemplate.getContent(), "I am no longer a potato");
        assertEquals(retrievedTemplate.getTitle(), "Not a potato template");

        testDb.delete();
    }

    @Test
    public void addTemplateCommentTest() {
        File testDb = new File("templateCommentTest.db");
        if (testDb.exists()) testDb.delete();

        DatabaseService testDatabase = new DatabaseService("templateCommentTest.db");
        testDatabase.initializeDatabase();

        long time = new Date().getTime();
        MadLibsTemplateComment comment = new MadLibsTemplateComment("1f", "absox", "comment comment", time);
        testDatabase.addTemplateComment(comment);
        List<MadLibsTemplateComment> retrievedComments = testDatabase.getCommentsOnTemplate("1f");

        assertEquals(retrievedComments.size(), 1);
        MadLibsTemplateComment retrievedComment = retrievedComments.get(0);
        assertEquals(retrievedComment.getTemplateId(), "1f");
        assertEquals(retrievedComment.getUser(), "absox");
        assertEquals(retrievedComment.getValue(), "comment comment");
        assertEquals(retrievedComment.getDate(), time);

        testDb.delete();
    }

    @Test
    public void votingTest() {
        File testDb = new File("templateRatingTest.db");
        if (testDb.exists()) testDb.delete();

        DatabaseService testDatabase = new DatabaseService("templateRatingTest.db");
        testDatabase.initializeDatabase();

        MadLibsTemplateRating rating = new MadLibsTemplateRating("absox", "0", "UP");

        testDatabase.updateRating(rating);

        MadLibsTemplateRating retrievedRating = testDatabase.getRating("absox", "0");

        assertNotNull(retrievedRating);
        assertEquals(retrievedRating.getUser(), "absox");
        assertEquals(retrievedRating.getTemplateId(), "0");
        assertEquals(retrievedRating.getRating(), "up");

        testDb.delete();
    }
}
