package CommandLineReader;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class MultiChoicePrompt extends Prompt {
    private static final String ILLEGAL_ANSWER_TEMPLATE = "Invalid answer of '%s'. Answer must be%s";
    private static final String OPTION_TEMPLATE = "\n \u2022 %s";
    private static final String VALUE_TEMPLATE = " '%s'%s";
    private Map<String, String> optionsAndValues;

    public MultiChoicePrompt(String variableType, String variableName, String text,
                             boolean isOptional, Map<String, String> optionsAndValues) {
        super(variableType, variableName, text, isOptional);
        this.optionsAndValues = optionsAndValues;
    }

    public MultiChoicePrompt(String variableType, String variableName, String text,
                             Map<String, String> optionsAndValues) {
        super(variableType, variableName, text);
        this.optionsAndValues = optionsAndValues;
    }

    public MultiChoicePrompt() {}

    @Override
    public Map<String, String> getOptionsAndValues() {
        return optionsAndValues;
    }

    @Override
    public String toString() {
        String options = getOptionsAsString();

        return getText() + options;
    }

    private String getOptionsAsString() {
        StringBuilder stringBuilder = new StringBuilder();

        for(Map.Entry<String, String> entry : optionsAndValues.entrySet()) {
            stringBuilder.append(String.format(OPTION_TEMPLATE, entry.getKey()));
        }

        return stringBuilder.toString();
    }

    private String getOptionsAsStringInline() {
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<Map.Entry<String, String>> entriesIterator = optionsAndValues.entrySet().iterator();

        Map.Entry<String, String> currEntry;

        while(entriesIterator.hasNext()) {
            currEntry = entriesIterator.next();
            if(entriesIterator.hasNext()) {
                stringBuilder.append(String.format(
                        VALUE_TEMPLATE, currEntry.getKey(), ",")
                );
            } else {
                stringBuilder.append(String.format(
                        " or" + VALUE_TEMPLATE, currEntry.getKey(), ".")
                );
            }
        }

        return stringBuilder.toString();
    }


    public void setOptionsAndValues(Map<String, String> optionsAndValues) {
        this.optionsAndValues = optionsAndValues;
    }

    @Override
    public void setAnswer(String answerKey) {
        if(optionsAndValues.containsKey(answerKey)) {
            String answerValue = optionsAndValues.get(answerKey);
            super.setAnswer(answerValue);
        } else {
            String exceptionMessage = String.format(ILLEGAL_ANSWER_TEMPLATE, answerKey, getOptionsAsStringInline());
            throw new IllegalArgumentException(exceptionMessage);
        }
    }
}