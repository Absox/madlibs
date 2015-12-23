package unit;

import com.madlibs.model.MadLibsTemplate;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Ran on 12/22/2015.
 */
public class MadLibsTemplateTest {

    @Test
    public void testIdEncoding() {
        MadLibsTemplate testTemplate = new MadLibsTemplate(0, "A template", "absox", "boop");
        assertEquals(testTemplate.getId(), "0");
        MadLibsTemplate testTemplate2 = new MadLibsTemplate(15, "A template", "absox", "boop");
        assertEquals(testTemplate2.getId(), "f");
    }

    @Test
    public void numBlanksCountTest() {

        String content = "<p>There once was a man called Fred. He had an\n" +
                "incredibly big [noun].</p>\n" +
                "\n" +
                "<p>He liked to eat [plural nouns] while [verb ending in 'ing'].</p>\n" +
                "\n" +
                "<p>Fred asked \"Why me? What did I do to deserve this?\"</p>\n" +
                "\n" +
                "<p>Fred awaited death with [adjective] [noun].</p>";

        MadLibsTemplate testTemplate = new MadLibsTemplate(0, "Fred Template", "absox", content);

        assertEquals(testTemplate.getNumBlanks(), 5);

    }



}
