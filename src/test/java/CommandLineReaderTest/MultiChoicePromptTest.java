package CommandLineReaderTest;

import CommandLineReader.MultiChoicePrompt;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class MultiChoicePromptTest {

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
}
