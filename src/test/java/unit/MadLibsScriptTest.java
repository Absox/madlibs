package unit;

import com.google.gson.Gson;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Ran on 1/2/2016.
 */
public class MadLibsScriptTest {

    @Test
    public void testJson() {
        List<String> list = new ArrayList<>();
        
        list.add("One");
        list.add("Two");
        
        Gson gson = new Gson();
        String serialized = gson.toJson(list);
        System.out.println(serialized);

        String[] strings = gson.fromJson(serialized, String[].class);
        System.out.println(Arrays.toString(strings));
    }
}
