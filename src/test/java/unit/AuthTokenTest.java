package unit;

import com.madlibs.authentication.AuthToken;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * AuthToken unit tests.
 * Created by Ran on 12/23/2015.
 */
public class AuthTokenTest {

    @Test
    public void testJson() {

        AuthToken testToken = new AuthToken("absox");
        String json = testToken.toJson();
        AuthToken fromJsonToken = AuthToken.fromJson(json);

        assertEquals(testToken, fromJsonToken);

    }

}
