package unit;

import com.madlibs.data.DatabaseService;
import com.madlibs.model.RegisteredUser;
import org.apache.commons.codec.DecoderException;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
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
}
