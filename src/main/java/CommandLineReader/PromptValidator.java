package CommandLineReader;

import com.fasterxml.jackson.databind.node.ObjectNode;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PromptValidator {
    private static final String PROMPT_TYPE = "promptType";
    private static final String METHOD_PREFIX = "validate";
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
    private final PromptSupplier promptSupplier = new PromptSupplier();


    public void validateJsonPrompt(ObjectNode node, int currIndex)
            throws IncorrectPromptFieldException, NoSuchMethodException, InvocationTargetException,
            IllegalAccessException {
        validatePromptType(getPromptTypeAsStringFromNode(node), currIndex);
        validateNodeFields(node, currIndex);

        String currField;
        String currValue;

        for(Iterator it = node.fieldNames(); it.hasNext();) {
            currField = it.next().toString();
            currValue = node.get(currField).asText();
            validateValueByField(currValue, currField, currIndex);
        }

    }

    public void validatePromptType(String promptType, int currIndex) throws IncorrectPromptFieldException {
        try {
            PromptType potentialPromptType = PromptType.valueOf(promptType);
        } catch(IllegalArgumentException exception) {
            throw new IncorrectPromptFieldException(
                    String.format(PROMPT_TYPE_EXCEPTION_MESSAGE_TEMPLATE, promptType, currIndex)
            );
        }
    }

    public void validateNodeFields(ObjectNode node, int currIndex) throws IncorrectPromptFieldException {
        Class promptClassToTestAgainst = getPromptClassByJsonNode(node);
        Map<String, Field> declaredAndInheritedFields = getCurrentAndParentClassFields(
                new HashMap<String, Field>(), promptClassToTestAgainst);

        for (Iterator<String> it = node.fieldNames(); it.hasNext();) {
            String field = it.next();
            if(field.equals("promptType")) continue;

            validateNodeDoesNotContainOptionsAndValuesIfBoolean(node, field, currIndex);
            validateNodeField(field, declaredAndInheritedFields, currIndex);
        }
    }

    public void validateValueByField(String valueToValidate, String correspondingField, int currIndex)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method validateMethod = getValidateMethodByFieldAsString(correspondingField);
        validateMethod.invoke(this, valueToValidate, currIndex);
    }

    private Class getPromptClassByJsonNode(ObjectNode node) {
        String promptTypeAsString = node.get(PROMPT_TYPE).asText();
        PromptType promptType = PromptType.valueOf(promptTypeAsString);
        Prompt prompt = promptSupplier.supplyPrompt(promptType);

        return prompt.getClass();
    }

    private Map<String, Field> getCurrentAndParentClassFields(Map<String, Field> fields, Class<?> type) {
        for(Field field : type.getDeclaredFields()) {
            fields.put(field.getName(), field);
        }

        if(type.getSuperclass() == null) {
            return fields;
        }

        return getCurrentAndParentClassFields(fields, type.getSuperclass());
    }

    private void validateNodeDoesNotContainOptionsAndValuesIfBoolean(ObjectNode node, String field, int currIndex)
            throws IncorrectPromptFieldException {
        String promptTypeAsString = getPromptTypeAsStringFromNode(node);

        if(promptTypeAsString.equals("BOOLEAN") && field.equals("optionsAndValues")) {
            throw new IncorrectPromptFieldException(
                    String.format(INVALID_PROPERTY_EXCEPTION_MESSAGE_TEMPLATE, field, currIndex)
            );
        }
    }

    private Method getValidateMethodByFieldAsString(String fieldAsString) throws NoSuchMethodException {
        String validateMethodName = getValidateMethodNameByField(fieldAsString);
        Method validateMethod = this.getClass().getMethod(validateMethodName, String.class, int.class);

        return validateMethod;
    }

    private String getValidateMethodNameByField(String fieldAsString) {
        String firstLetterOfFieldToUpperCase = fieldAsString.substring(0, 1).toUpperCase();
        String restOfFieldInCamelCase = fieldAsString.substring(1);

        return METHOD_PREFIX + firstLetterOfFieldToUpperCase + restOfFieldInCamelCase;
    }

    public void validateNodeField(String fieldToValidate, Map<String, Field> declaredAndInheritedFields, int currIndex)
            throws IncorrectPromptFieldException {

        Field potentialField = declaredAndInheritedFields.get(fieldToValidate);
        if(potentialField == null) {
            throw new IncorrectPromptFieldException(
                    String.format(INVALID_PROPERTY_EXCEPTION_MESSAGE_TEMPLATE, fieldToValidate, currIndex)
            );
        }
    }

    public void validateVariableType(String variableType, int currIndex) throws IncorrectPromptFieldException {
        try {
            VariableType potentialVariableType = VariableType.valueOf(variableType);
        } catch(IllegalArgumentException exception) {
            throw new IncorrectPromptFieldException(
                    String.format(VARIABLE_TYPE_EXCEPTION_MESSAGE_TEMPLATE, variableType, currIndex)
            );
        }
    }

    public void validateText(String promptText, int currIndex) throws IncorrectPromptFieldException {
        if(promptText.length() < MIN_PROMPT_LENGTH || promptText.length() > MAX_PROMPT_LENGTH) {
            throw new IncorrectPromptFieldException(
                    String.format(PROMPT_TEXT_EXCEPTION_MESSAGE_TEMPLATE, promptText, currIndex, MIN_PROMPT_LENGTH,
                            MAX_PROMPT_LENGTH)
            );
        }
    }

    public void validateIsOptional(String isOptional, int currIndex) throws IncorrectPromptFieldException {
        if(isOptional.toLowerCase().equals("true") || isOptional.toLowerCase().equals("false")) {
            return;
        } else {
            throw new IncorrectPromptFieldException(
                    String.format(IS_OPTIONAL_EXCEPTION_MESSAGE_TEMPLATE, isOptional, currIndex)
            );
        }

    }

    public void validateVariableName(String variableName, int currIndex) throws IncorrectPromptFieldException {}

    private String getPromptTypeAsStringFromNode(ObjectNode node) {
        String promptTypeAsString = node.get(PROMPT_TYPE).asText();
        return promptTypeAsString;
    }

}
