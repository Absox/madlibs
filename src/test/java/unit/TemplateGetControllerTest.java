package unit;

import com.google.gson.JsonObject;
import com.madlibs.model.MadLibsTemplate;
import com.madlibs.model.MadLibsTemplateComment;
import com.madlibs.server.TemplateGetController;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Ran on 12/23/2015.
 */
public class TemplateGetControllerTest {

    @Test
    public void testJsonConversion() {

        MadLibsTemplate testTemplate = new MadLibsTemplate(0, "A test template", "absox", "boop boop woop woop");
        List<MadLibsTemplateComment> comments = new ArrayList<>();
        comments.add(new MadLibsTemplateComment("0", "absox", "asdf", 123L));

        JsonObject testObject = TemplateGetController.templateToJson(testTemplate, comments);
        assertEquals(testObject.toString(), "{\"status\":\"success\",\"title\":\"A test template\",\"creator\":\"absox\",\"rating\":0,\"value\":\"boop boop woop woop\",\"comments\":[{\"templateId\":\"0\",\"user\":\"absox\",\"value\":\"asdf\",\"date\":123}]}");

    }
}
