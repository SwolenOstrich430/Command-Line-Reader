package CommandLineReaderTest;

import CommandLineReader.IncorrectPromptFieldException;
import CommandLineReader.PromptMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class PromptMapperTest {


//    @Test
//    public void getPromptsAsJson() {
//        try {
//            PromptMapper promptMapper = new PromptMapper();
//            ObjectNode[] promptsAsJson = promptMapper.getPromptsAsJson("src/main/resources/test-prompts.json");
//
//            assertEquals(8, promptsAsJson.length);
//        } catch(Throwable throwable) {
//
//        }
//    }
//
//    @Test
//    public void getPromptsAsJsonAssertThrowsIOException() {
//        PromptMapper promptMapper = new PromptMapper();
//
//        Throwable exception = assertThrows(IOException.class, () -> promptMapper.getPromptsAsJson("blah"));
//        assertEquals("blah (No such file or directory)", exception.getMessage());
//    }
//
//    @Test
//    public void getPromptsAsJsonMalformedJson() {
//        PromptMapper promptMapper = new PromptMapper();
//
//        Throwable exception = assertThrows(MismatchedInputException.class, () -> promptMapper.getPromptsAsJson(
//                "src/main/resources/test-prompts-malformed-json.json"));
//
//    }

    @Test
    public void getPromptsIncorrectPromptType() {
        PromptMapper promptMapper = new PromptMapper();

        Throwable excepction = assertThrows(IncorrectPromptFieldException.class, () -> promptMapper.getPrompts(
                "src/main/resources/test-prompts-incorrect-fields-prompt-type.json"));

        assertEquals("Invalid prompt type of NOT_VALID_TYPE at index 0. " +
                            "Prompt type must be 'CLASSIC', 'BOOLEAN', or 'MULTIPLE_CHOICE'", excepction.getMessage());

    }
//
//    @Test
//    public void getPromptsIncorrectVariableType() {
//        PromptMapper promptMapper = new PromptMapper();
//
//        Throwable exception = assertThrows(IncorrectPromptFieldException.class, () -> promptMapper.getPrompts(
//                "src/main/resources/text-prompts-incorrect-variable-type.json"
//        ));
//
//        assertEquals("Invalid variable type of long at index 0. Variable type must be 'STRING', 'BOOLEAN', " +
//                "'INTEGER', or 'DOUBLE'", exception.getMessage());
//    }
//
//    @Test
//    public void getPromptsInvalidPromptText() {
//        PromptMapper promptMapper = new PromptMapper();
//
//        Throwable exception = assertThrows(IncorrectPromptFieldException.class, () -> promptMapper.getPrompts(
//                "src/main/resources/test-prompts-invalid-prompt-text.json"
//        ));
//
//        assertEquals("Invalid prompt text of '0012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567891234567891' at index 0. Prompt text must be between 10 and 140 " +
//                "characters", exception.getMessage());
//    }
//
//    @Test
//    public void getPromptsInvalidIsOptional() {
//        PromptMapper promptMapper = new PromptMapper();
//
//        Throwable exception = assertThrows(IncorrectPromptFieldException.class, () -> promptMapper.getPrompts(
//                "src/main/resources/test-prompts-invalid-is-optional.json"
//        ));
//
//        assertEquals("Invalid isOptional value of 'balls' at index 0. Value must be true or false, or the " +
//                "key-value pair must be excluded to default to false.", exception.getMessage());
//    }
//
//    @Test
//    public void getPromptsInvalidFieldsClassic() {
//        PromptMapper promptMapper = new PromptMapper();
//
//        Throwable exception = assertThrows(IncorrectPromptFieldException.class, () -> promptMapper.getPrompts(
//                "src/main/resources/test-prompts-invalid-fields-classic.json"
//        ));
//
//        assertEquals("Invalid field of 'thisFieldIsntReal' at index 0.", exception.getMessage());
//    }
//
//    @Test
//    public void getPromptsBooleanWithSpecifiedOptionsAndValues() {
//        PromptMapper promptMapper = new PromptMapper();
//
//        Throwable exception = assertThrows(IncorrectPromptFieldException.class, () -> promptMapper.getPrompts(
//                "src/main/resources/test-prompts-boolean-specified-options-and-values.json"
//        ));
//
//        assertEquals("Invalid field of 'optionsAndValues' at index 0.", exception.getMessage());
//    }
//

}
