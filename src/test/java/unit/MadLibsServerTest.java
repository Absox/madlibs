package unit;

import com.madlibs.authentication.AuthToken;
import com.madlibs.server.MadLibsServer;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Server unit tests.
 * Created by Ran on 12/23/2015.
 */
public class MadLibsServerTest {

    @Test
    public void authTokenTest() {

        MadLibsServer testServer = new MadLibsServer();
        AuthToken testToken = testServer.issueToken("absox");
        AuthToken newToken = testServer.authenticate(testToken);
        assertNotNull(testToken);
        assertNull(testServer.authenticate(testToken));
        assertNotNull(testServer.authenticate(newToken));

    }
}
