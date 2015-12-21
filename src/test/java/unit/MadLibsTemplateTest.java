package unit;

import com.madlibs.model.MadLibsTemplate;
import com.madlibs.server.User;
import org.junit.Test;

/**
 * Created by Ran on 12/20/2015.
 */
public class MadLibsTemplateTest {

    @Test
    public void test() {

        new MadLibsTemplate(new User("Absox"));

    }
}
