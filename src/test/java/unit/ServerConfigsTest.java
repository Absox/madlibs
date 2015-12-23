package unit;

import com.madlibs.config.ServerConfigs;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Server configs object unit tests.
 * Created by Ran on 12/22/2015.
 */
public class ServerConfigsTest {

    @Test
    public void testCounters() {

        ServerConfigs testConfigs = new ServerConfigs(0, 0);
        assertEquals(testConfigs.getNextScriptId(), 0);
        assertEquals(testConfigs.getScriptId(), 1);
        assertEquals(testConfigs.getNextTemplateId(), 0);
        assertEquals(testConfigs.getTemplateId(), 1);

    }
}
