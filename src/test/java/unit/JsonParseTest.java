package unit;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.Test;

import static org.junit.Assert.assertNull;

/**
 * Created by Ran on 12/28/2015.
 */
public class JsonParseTest {

    @Test
    public void testParseJson() {

        MemberClass testMember = new MemberClass("A", 1);
        TestClass testObject = new TestClass("B", testMember);

        Gson gson = new Gson();
        System.out.println(gson.toJson(testObject));

        String testString = "{\n" +
                "    \"attribute\" : \"a\",\n" +
                "    \"member\" : {\n" +
                "        \"attribute\" : \"b\",\n" +
                "        \"anotherAttribute\" : 3\n" +
                "    }\n" +
                "}";

        JsonParser parser = new JsonParser();
        JsonElement parsedResponse = parser.parse(testString);
        JsonObject parsedResponseObject = parsedResponse.getAsJsonObject();

        System.out.println(parsedResponseObject.get("member").getAsJsonObject());
    }

    @Test
    public void nullTest() {
        String testString = "{\n" +
                "    \"attribute\" : \"a\"\n" +
                "}";
        JsonParser parser = new JsonParser();

        JsonObject parsedResponseObject = parser.parse(testString).getAsJsonObject();

        JsonElement test = parsedResponseObject.get("member");

        assertNull(test);

    }

    private class TestClass {
        String attribute;
        MemberClass member;

        private TestClass(String attribute, MemberClass member) {
            this.attribute = attribute;
            this.member = member;
        }
    }

    private class MemberClass {
        String attribute;
        int anotherAttribute;

        private MemberClass(String attribute, int anotherAttribute) {
            this.attribute = attribute;
            this.anotherAttribute = anotherAttribute;
        }
    }
}
