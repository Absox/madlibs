package unit;

import com.madlibs.model.MadLibsSession;
import com.madlibs.model.MadLibsTemplate;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Ran on 12/25/2015.
 */
public class MadLibsSessionTest {

    private MadLibsSession testSession;
    private MadLibsTemplate testTemplate;
    private String testTemplateBody;

    @Before
    public void init() {
        testTemplateBody = "<p>There once was a man called Fred. He had an\n" +
                "incredibly big [noun].</p>\n" +
                "\n" +
                "<p>He liked to eat [plural nouns] while [verb ending in 'ing'].</p>\n" +
                "\n" +
                "<p>Fred asked \"Why me? What did I do to deserve this?\"</p>\n" +
                "\n" +
                "<p>Fred awaited death with [adjective] [noun].</p>";
        testTemplate = new MadLibsTemplate("0", "Test Template", "absox", 0, testTemplateBody);
        testSession = new MadLibsSession("0", "absox", testTemplate);
    }

    @Test
    public void testBasic() {
        assertEquals(testSession.getHost(), "absox");
        assertEquals(testSession.getCurrentPrompt(), "[noun]");

        testSession.participantJoin("absox", null);
        assertEquals(testSession.getCurrentParticipant().getIdentifier(), "absox");
    }
}
