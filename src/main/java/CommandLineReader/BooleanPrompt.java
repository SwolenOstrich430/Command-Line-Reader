package CommandLineReader;

import java.util.HashMap;
import java.util.Map;

public class BooleanPrompt extends Prompt {
    public static final String AFFIRMATIVE_ANSWER = "yes";
    public static final String NEGATIVE_ANSWER = "no";
    private static final String ILLEGAL_ARG_EXCEPTION_TEMPLATE = "Cannot create answer boolean prompt with %s. " +
            "Must be '" + AFFIRMATIVE_ANSWER + "' or '" + NEGATIVE_ANSWER + "'.";
    private final Map<String, Boolean> optionsAndValues = new HashMap<String, Boolean>() {{
            put(AFFIRMATIVE_ANSWER, true);
            put(NEGATIVE_ANSWER, false);
    }};


    public BooleanPrompt(String variableName, String text, boolean isOptional) {
        super("BOOLEAN", variableName, text, isOptional);
    }

    public BooleanPrompt(String variableName, String text) {
        super("boolean", variableName, text);
    }

    public BooleanPrompt() {}

    @Override
    public String toString() {
        String promptAsString = String.format(getText() + " Answer '%s' or '%s'.", AFFIRMATIVE_ANSWER, NEGATIVE_ANSWER);
        return promptAsString;
    }

    @Override
    public Map<String, Boolean> getOptionsAndValues() {
        return optionsAndValues;
    }

    @Override
    public void setAnswer(String answer) throws IllegalArgumentException {
        String answerAsLowerCase = answer.toLowerCase();

        if(answerAsLowerCase.startsWith(AFFIRMATIVE_ANSWER.substring(0, 1))) {
            super.setAnswer(AFFIRMATIVE_ANSWER);
            return;
        }

        if(answerAsLowerCase.startsWith(NEGATIVE_ANSWER.substring(0, 1))) {
            super.setAnswer(NEGATIVE_ANSWER);
            return;
        }

        String illegalArgumentExceptionMessage = String.format(ILLEGAL_ARG_EXCEPTION_TEMPLATE, answer);
        throw new IllegalArgumentException(illegalArgumentExceptionMessage);
    }

    @Override
    public <T> T getAnswerAsVariableType() {
        String answerAsString = super.getAnswer();
        boolean answerAsBoolean = optionsAndValues.get(answerAsString);

        return (T) Boolean.valueOf(answerAsBoolean);
    }
}
