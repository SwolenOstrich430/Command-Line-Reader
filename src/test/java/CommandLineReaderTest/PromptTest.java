package CommandLineReaderTest;

import CommandLineReader.BooleanPrompt;
import CommandLineReader.MultiChoicePrompt;
import CommandLineReader.Prompt;
import CommandLineReader.VariableType;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class PromptTest {

    @Test
    public void testToStringClassic() {
        Prompt prompt = new Prompt("STRING", "aVariableName", "What's your favorite color?",
                false);

        assertEquals("What's your favorite color?", prompt.toString());
    }

    @Test
    public void getAnswerAsVariableTypeString() {
        Prompt prompt = new Prompt("STRING", "aVariableName", "What's your favorite color?",
                false);
        prompt.setAnswer("yellow");

        assertTrue(prompt.getAnswerAsVariableType() instanceof String);
    }

    @Test
    public void getAnswerAsVariableTypeInteger() {
        Prompt prompt = new Prompt("INTEGER", "aVariableName", "What's your favorite number?",
                false);
        prompt.setAnswer("1");

        assertTrue(prompt.getAnswerAsVariableType() instanceof Integer);
    }

    @Test
    public void getAnswerAsVariableTypeDouble() {
        Prompt prompt = new Prompt("DOUBLE", "aVariableName", "What's your favorite number?",
                false);
        prompt.setAnswer("1.0");

        assertTrue(prompt.getAnswerAsVariableType() instanceof Double);
    }

    @Test
    public void getAnswerAsVariableTypeBoolean() {
        Prompt prompt = new Prompt("BOOLEAN", "aVariableName", "Is the sky blue?",
                false);
        prompt.setAnswer("no");

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> prompt.getAnswerAsVariableType());

        assertEquals("Please use BooleanPrompt if you want to return a boolean value " +
                "from a Prompt object.", exception.getMessage());
    }
}
