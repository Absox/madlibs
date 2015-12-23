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

        MadLibsTemplate testTemplate = new MadLibsTemplate(0, "absox");
        assertEquals(testTemplate.getId(), "0");
        MadLibsTemplate testTemplate2 = new MadLibsTemplate(15, "absox");
        assertEquals(testTemplate2.getId(), "f");

    }



}
