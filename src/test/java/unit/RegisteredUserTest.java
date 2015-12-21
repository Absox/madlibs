package unit;

import com.madlibs.server.RegisteredUser;
import org.apache.commons.codec.DecoderException;
import org.junit.Test;

import java.io.IOException;
import java.util.Random;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by Ran on 12/20/2015.
 */
public class RegisteredUserTest {

    @Test
    public void testSaltedHashedPassword() {

        Random rng = new Random(0);

        for (int c = 0; c < 100; c++) {

            String username = "testUser" + c;
            byte[] randomBytes = new byte[25];
            rng.nextBytes(randomBytes);
            String password = new String(randomBytes);

            try {
                RegisteredUser testUser = new RegisteredUser(username, password);

                if (!testUser.validatePassword(password)) {
                    System.out.println("On trial " + c + " failed with password: " + password);
                }

                assertTrue(testUser.validatePassword(password));
                randomBytes[0] += 1;
                assertFalse(testUser.validatePassword(new String(randomBytes)));
            } catch (DecoderException e) {
                fail();
            } catch (IOException e) {
                fail();
            }


        }

    }
}
