package CommandLineReader;

import java.util.Map;

public class MultiChoicePrompt extends Prompt {

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

    public void setOptionsAndValues(Map<String, String> optionsAndValues) {
        this.optionsAndValues = optionsAndValues;
    }
}