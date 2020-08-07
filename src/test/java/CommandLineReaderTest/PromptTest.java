package CommandLineReaderTest;

import CommandLineReader.BooleanPrompt;
import CommandLineReader.MultiChoicePrompt;
import CommandLineReader.Prompt;
import CommandLineReader.VariableType;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class PromptTest {

    @Test
    public void testToStringClassic() {
        Prompt prompt = new Prompt("STRING", "aVariableName", "What's your favorite color?",
                false);

        assertEquals("What's your favorite color?", prompt.toString());
    }

}
