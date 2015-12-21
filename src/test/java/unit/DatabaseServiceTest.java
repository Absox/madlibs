package unit;

import com.madlibs.data.DatabaseService;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;

import java.io.File;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by Ran on 12/20/2015.
 */
public class DatabaseServiceTest {

    @Test
    public void basicFunctionalityTest() {
        File testFile = new File("test.db");

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
}
