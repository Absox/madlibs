package unit;

import com.madlibs.model.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
        assertEquals(testSession.getTemplateContent(), testTemplateBody);
    }

    @Test
    public void testJoin() {
        testSession.participantJoin("absox", null);
        testSession.participantJoin("sam", null);

        MadLibsSessionParticipant absox = testSession.getParticipantByIdentifier("absox");
        assertEquals(absox.getIdentifier(), "absox");

        assertEquals(testSession.getNumParticipants(), 2);
        assertEquals(testSession.getCurrentParticipant().getIdentifier(), "absox");

        testSession.participantLeave("absox");
        assertEquals(testSession.getCurrentParticipant().getIdentifier(), "sam");
    }

    @Test
    public void testResponses() {
        testSession.participantJoin("absox", null);
        testSession.participantJoin("sam", null);

        assertEquals(testSession.getCurrentPrompt(), "[noun]");
        assertEquals(testSession.getCurrentParticipant().getIdentifier(), "absox");

        testSession.addResponse("banana");
        assertEquals(testSession.getCurrentPrompt(), "[plural nouns]");
        assertEquals(testSession.getCurrentParticipant().getIdentifier(), "sam");

        testSession.addResponse("peanuts");
        assertEquals(testSession.getCurrentPrompt(), "[verb ending in 'ing']");
        assertEquals(testSession.getCurrentParticipant().getIdentifier(), "absox");

        testSession.addResponse("running");
        testSession.addResponse("beautiful");
        testSession.addResponse("gorilla");

        assertNull(testSession.getCurrentPrompt());

        List<MadLibsResponse> responses = testSession.getResponses();
        assertEquals(responses.size(), 5);

        MadLibsResponse testResponse = responses.get(3);
        assertEquals(testResponse.getValue(), "beautiful");
        assertEquals(testResponse.getUser(), "sam");
    }

    @Test
    public void testChat() {
        long time = System.currentTimeMillis();
        testSession.addChatMessage(new ChatMessage(time, "absox", "Hello, world!"));

        List<ChatMessage> chatLog = testSession.getChatLog();
        assertEquals(chatLog.size(), 1);
        assertEquals(chatLog.get(0).getUser(), "absox");
        assertEquals(chatLog.get(0).getValue(), "Hello, world!");
        assertEquals(chatLog.get(0).getDateStamp(), time);
    }
}
