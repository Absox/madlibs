import com.madlibs.server.RegisteredUser;
import org.apache.commons.codec.DecoderException;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertTrue;

/**
 * Created by Ran on 12/20/2015.
 */
public class RegisteredUserTest {

    @Test
    public void testSaltedHashedPassword() {

        Random rng = new Random(0);

        for (int c = 0; c < 100; c++) {

            String username = "testUser" + c;
            byte[] randomBytes = new byte[15];
            rng.nextBytes(randomBytes);
            String password = new String(randomBytes);

            RegisteredUser testUser = new RegisteredUser(username, password);

            try {
                if (!testUser.validatePassword(password)) {
                    System.out.println("On trial " + c + " failed with password: " + password);
                }

                assertTrue(testUser.validatePassword(password));
            } catch (DecoderException e) {

            }


        }

    }
}
