package unit;

import com.madlibs.model.RegisteredUser;
import org.apache.commons.codec.DecoderException;
import org.junit.Test;

import java.io.IOException;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Created by Ran on 12/20/2015.
 */
public class RegisteredUserTest {

    @Test
    public void testSaltedHashedPassword() {

        Random rng = new Random(0);

        for (int c = 0; c < 100; c++) {

            String username = "testUser" + c;
            byte[] randomBytes = new byte[10];
            rng.nextBytes(randomBytes);
            String password = new String(randomBytes);

            try {
                RegisteredUser testUser = new RegisteredUser(username, password);

                if (!testUser.validatePassword(password)) {
                    System.out.println("On trial " + c + " failed with password: " + password);
                }

                assertTrue(testUser.validatePassword(password));
                String newPassword = password.concat(" ");
                assertNotEquals(password, newPassword);
                assertFalse(testUser.validatePassword(newPassword));
            } catch (DecoderException e) {
                fail();
            } catch (IOException e) {
                fail();
            }


        }

    }
}
