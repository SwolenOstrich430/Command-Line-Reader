package CommandLineReaderTest;

import CommandLineReader.MultiChoicePrompt;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class MultiChoicePromptTest {

    private final Map<String, String> optionsAndValues = new LinkedHashMap<String, String>() {{
        put("yellow", "yellow");
        put("red", "red");
        put("brown", "brown");
    }};


    @Test
    public void testToStringMultipleChoice() {
        Map<String, String> optionsAndValues = new LinkedHashMap<String, String>() {{
            put("yellow", "yellow");
            put("red", "red");
            put("brown", "brown");
        }};

        MultiChoicePrompt multiChoicePrompt = new MultiChoicePrompt("STRING", "aVariableName",
                "What's your favorite color?", false, optionsAndValues);

        String expectedString = "What's your favorite color?\n \u2022 yellow\n \u2022 red\n \u2022 brown";
        assertEquals(expectedString, multiChoicePrompt.toString());
    }

    @Test
    public void setAnswer() {
        MultiChoicePrompt multiChoicePrompt = new MultiChoicePrompt("STRING", "aVariableName",
                "What's your favorite color?", false, optionsAndValues);

        multiChoicePrompt.setAnswer("yellow");
        assertEquals("yellow", multiChoicePrompt.getAnswer());
    }

    @Test
    public void setAnswerExpectIllegalArgumentException() {
        MultiChoicePrompt multiChoicePrompt = new MultiChoicePrompt("STRING", "aVariableName",
                "What's your favorite color?", false, optionsAndValues);

        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
                multiChoicePrompt.setAnswer("notAValidAnswer"));

        assertEquals("Invalid answer of 'notAValidAnswer'. Answer must be 'yellow', 'red', or 'brown'.",
                exception.getMessage());
    }
}
