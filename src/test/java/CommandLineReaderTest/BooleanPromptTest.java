package CommandLineReaderTest;

import CommandLineReader.BooleanPrompt;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BooleanPromptTest {

    @Test
    public void testToStringBoolean() {
        BooleanPrompt booleanPrompt = new BooleanPrompt("aVariableName",
                "Is your favorite color yellow?", false);

        assertEquals("Is your favorite color yellow? Answer 'yes' or 'no'.", booleanPrompt.toString());
    }

    @Test
    public void getAnswerAsVariableType() {
        BooleanPrompt booleanPrompt = new BooleanPrompt("aVariableName",
                "Is your favorite color yellow?", false);
        booleanPrompt.setAnswer("yes");
        assertTrue(booleanPrompt.getAnswerAsVariableType() instanceof Boolean);
    }
}
