package CommandLineReaderTest;

import CommandLineReader.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class PromptSupplierTest {

    private final PromptSupplier promptSupplier = new PromptSupplier();
    private final List<PromptType> promptTypes = new ArrayList<PromptType>(
                        Arrays.asList(PromptType.CLASSIC, PromptType.BOOLEAN, PromptType.MULTIPLE_CHOICE)
                    );
    private final List<Class> promptClasses  = new ArrayList<Class>(
                        Arrays.asList(Prompt.class, BooleanPrompt.class, MultiChoicePrompt.class)
                    );


    @Test
    public void getPrompt() {
        Prompt expectedClassicPrompt;

        for(int i = 0; i < promptTypes.size(); i++) {
            expectedClassicPrompt = promptSupplier.supplyPrompt(promptTypes.get(i));
            assertEquals(expectedClassicPrompt.getClass(), promptClasses.get(i));
        }

    }

    @Test
    public void getClassicPromptWithJson() {
        String classicPromptAsJson = "{\"variableType\": \"STRING\",\"variableName\": \"issql\",\"text\": " +
                " \"is your database relational?\", \"isOptional\": false}";
        Prompt classicPrompt = promptSupplier.supplyPrompt(PromptType.CLASSIC, classicPromptAsJson).get();
        assertEquals(classicPrompt.getVariableType(), "String");
        assertEquals(classicPrompt.getVariableName(), "issql");
        assertEquals(classicPrompt.getText(), "is your database relational?");
        assertEquals(classicPrompt.isOptional(), false);
    }

    @Test
    public void getBooleanPromptWithJson() {
        String booleanPromptAsJson = "{\"variableType\": \"BOOLEAN\",\"variableName\": \"issql\",\"text\": " +
                "\"is your database relational?\", \"isOptional\": false}";

        Prompt booleanPrompt = promptSupplier.supplyPrompt(PromptType.BOOLEAN, booleanPromptAsJson).get();

        assertEquals(booleanPrompt.getVariableType(), "boolean");
        assertEquals(booleanPrompt.getVariableName(), "issql");
        assertEquals(booleanPrompt.getText(), "is your database relational?");
        assertEquals(booleanPrompt.isOptional(), false);

        assertEquals(booleanPrompt.getOptionsAndValues().get("yes"), true);
        assertEquals(booleanPrompt.getOptionsAndValues().get("no"), false);
    }

    @Test
    public void getMultiChoicePromptWithJson() {
        String multiChoicePromptAsString = "{\"variableType\": \"INTEGER\",\"variableName\": \"issql\",\"text\": " +
                "\"is your database relational?\", \"isOptional\": false, \"optionsAndValues\": {\"one\": \"1\", " +
                "\"two\": \"2\", \"three\": \"3\"}}";

        Prompt multiChoicePrompt = promptSupplier.supplyPrompt(PromptType.MULTIPLE_CHOICE, multiChoicePromptAsString).get();

        assertEquals(multiChoicePrompt.getVariableType(), "integer");
        assertEquals(multiChoicePrompt.getVariableName(), "issql");
        assertEquals(multiChoicePrompt.getText(), "is your database relational?");
        assertEquals(multiChoicePrompt.isOptional(), false);

        assertEquals(multiChoicePrompt.getOptionsAndValues().get("one"), "1");
        assertEquals(multiChoicePrompt.getOptionsAndValues().get("two"), "2");
        assertEquals(multiChoicePrompt.getOptionsAndValues().get("three"), "3");
    }

    @Test
    public void getPromptWithMalformedJson() {
        String malformedPrompt = "{\"{{}}}{{{variableType\": \"STRING\",\"variableName\": \"issql\",\"text\": " +
                " \"is your database relational?\", \"isOptional\": false}";

        Optional<Prompt> nullPrompt = promptSupplier.supplyPrompt(PromptType.CLASSIC, malformedPrompt);
        assertEquals(false, nullPrompt.isPresent());
    }

    @Test
    public void getPromptWithIncorrectFields() {
        String malformedPrompt = "{\"notvairabletype\": \"STRING\",\"variableName\": \"issql\",\"text\": " +
                " \"is your database relational?\", \"isOptional\": false}";

        Optional<Prompt> nullPrompt = promptSupplier.supplyPrompt(PromptType.CLASSIC, malformedPrompt);
        assertEquals(false, nullPrompt.isPresent());
    }
}
