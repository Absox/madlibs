package unit;

import com.madlibs.config.AnonymousIdentifiers;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Unit tests for anonymous identifiers.
 * Created by Ran on 12/24/2015.
 */
public class AnonymousIdentifiersTest {

    @Test
    public void testFileInput() {

        try {
            AnonymousIdentifiers identifiers = new AnonymousIdentifiers("testResources/testCelebs");
            List<String> identifierStrings = identifiers.getIdentifiers();
            assertEquals(identifierStrings.size(), 3);
            assertEquals(identifierStrings.get(0), "Nic_Cage");
            assertEquals(identifierStrings.get(1), "James_Franco");
            assertEquals(identifierStrings.get(2), "Sean_Bean");

        } catch (FileNotFoundException e) {
            fail();
        }

    }

    @Test
    public void rngTest() {

        try {
            AnonymousIdentifiers identifiers = new AnonymousIdentifiers("testResources/testCelebs");
            for (int c = 0; c < 5; c++) {
                System.out.println(identifiers.getRandomIdentifier());
            }

        } catch (FileNotFoundException e) {
            fail();
        }

    }
}
