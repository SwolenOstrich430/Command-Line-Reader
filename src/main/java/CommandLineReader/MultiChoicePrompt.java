package CommandLineReader;

import java.util.Map;


public class MultiChoicePrompt extends Prompt {

    private static final String OPTION_TEMPLATE = "\n \u2022 %s";
    private static final String VALUE_TEMPLATE = ", %s";
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

        for(Map.Entry<String, String> entry : getOptionsAndValues().entrySet()) {
            stringBuilder.append(String.format(OPTION_TEMPLATE, entry.getKey()));
        }

        return stringBuilder.toString();
    }


    public void setOptionsAndValues(Map<String, String> optionsAndValues) {
        this.optionsAndValues = optionsAndValues;
    }
}