package CommandLineReader;

import com.fasterxml.jackson.databind.node.ObjectNode;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PromptValidator {
    private static final String PROMPT_TYPE = "promptType";
    private static final int MIN_PROMPT_LENGTH = 10;
    private static final int MAX_PROMPT_LENGTH = 140;
    private static final String PROMPT_TYPE_EXCEPTION_MESSAGE_TEMPLATE = "Invalid prompt type of %s at index %d. " +
            "Prompt type must be 'CLASSIC', 'BOOLEAN', or 'MULTIPLE_CHOICE'";
    private static final String VARIABLE_TYPE_EXCEPTION_MESSAGE_TEMPLATE = "Invalid variable type of %s at index %d. " +
            "Variable type must be 'STRING', 'BOOLEAN', 'INTEGER', or 'DOUBLE'";
    private static final String PROMPT_TEXT_EXCEPTION_MESSAGE_TEMPLATE = "Invalid prompt text of '%s' at index %d. " +
            "Prompt text must be between %d and %d characters";
    private static final String IS_OPTIONAL_EXCEPTION_MESSAGE_TEMPLATE = "Invalid isOptional value of '%s' at index " +
            "%d. Value must be true or false, or the key-value pair must be excluded to default to false.";
    private static final String INVALID_PROPERTY_EXCEPTION_MESSAGE_TEMPLATE = "Invalid field of '%s' " +
            "at index %d.";

    public static void validateJsonPrompt(ObjectNode node, int currIndex) throws IncorrectPromptFieldException {
        String promptType = node.get(PROMPT_TYPE).asText();
        validatePromptType(promptType, currIndex);

        validateFields(promptType, node, currIndex);

        String variableType = node.get("variableType").asText();
        validateVariableType(variableType, currIndex);

        String promptText = node.get("text").asText();
        validatePromptText(promptText, currIndex);

        if(node.has("isOptional")) {
            String isOptionalText = node.get("isOptional").asText();
            validateIsOptional(isOptionalText, currIndex);
        }
    }

    public static void validateFields(String promptType, ObjectNode node, int currIndex) throws IncorrectPromptFieldException {
        PromptSupplier promptSupplier = new PromptSupplier();
        Class promptClassToTestAgainst = promptSupplier.supplyPrompt(PromptType.valueOf(promptType)).getClass();
        Map<String, Field> declaredAndInheritedFields = getAllFields(new HashMap<String, Field>(), promptClassToTestAgainst);


        for (Iterator<String> it = node.fieldNames(); it.hasNext(); ) {
            String field = it.next();
            if(field == "promptType") continue;

            if(promptType.equals("BOOLEAN") && field.equals("optionsAndValues")) {
                throw new IncorrectPromptFieldException(
                        String.format(INVALID_PROPERTY_EXCEPTION_MESSAGE_TEMPLATE, field, currIndex)
                );
            }

            Field potentialField = declaredAndInheritedFields.get(field);
            if(potentialField == null) {
                throw new IncorrectPromptFieldException(
                        String.format(INVALID_PROPERTY_EXCEPTION_MESSAGE_TEMPLATE, field, currIndex)
                );
            }
        }
    }

    private static Map<String, Field> getAllFields(Map<String, Field> fields, Class<?> type) {
        for(Field field : type.getDeclaredFields()) {
            fields.put(field.getName(), field);
        }

        if(type.getSuperclass() == null) {
            return fields;
        }

        return getAllFields(fields, type.getSuperclass());
    }

    private static void validateVariableType(String variableType, int currIndex) throws IncorrectPromptFieldException {
        try {
            VariableType potentialVariableType = VariableType.valueOf(variableType);
        } catch(IllegalArgumentException exception) {
            throw new IncorrectPromptFieldException(
                    String.format(VARIABLE_TYPE_EXCEPTION_MESSAGE_TEMPLATE, variableType, currIndex)
            );
        }
    }

    private static void validatePromptType(String promptType, int currIndex) throws IncorrectPromptFieldException {
       try {
           PromptType potentialPromptType = PromptType.valueOf(promptType);
       } catch(IllegalArgumentException exception) {
           throw new IncorrectPromptFieldException(
                   String.format(PROMPT_TYPE_EXCEPTION_MESSAGE_TEMPLATE, promptType, currIndex)
           );
       }
    }

    private static void validatePromptText(String promptText, int currIndex) throws IncorrectPromptFieldException {
        if(promptText.length() < MIN_PROMPT_LENGTH || promptText.length() > MAX_PROMPT_LENGTH) {
            throw new IncorrectPromptFieldException(
                    String.format(PROMPT_TEXT_EXCEPTION_MESSAGE_TEMPLATE, promptText, currIndex, MIN_PROMPT_LENGTH,
                            MAX_PROMPT_LENGTH)
            );
        }
    }

    private static void validateIsOptional(String isOptional, int currIndex) throws IncorrectPromptFieldException {
        if(isOptional.toLowerCase().equals("true") || isOptional.toLowerCase().equals("false")) {
            return;
        } else {
            throw new IncorrectPromptFieldException(
                    String.format(IS_OPTIONAL_EXCEPTION_MESSAGE_TEMPLATE, isOptional, currIndex)
            );
        }

    }
}
