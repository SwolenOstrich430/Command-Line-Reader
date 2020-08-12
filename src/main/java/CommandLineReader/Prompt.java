package CommandLineReader;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Prompt {

    private VariableType variableType;
    private String variableName;
    private String text;
    private boolean isOptional;
    private String answer;

    public Prompt(String variableType, String variableName, String text, boolean isOptional) {
        this.variableType = VariableType.valueOf(variableType);
        this.variableName = variableName;
        this.text = text;
        this.isOptional = isOptional;
        this.answer = "";
    }

    Prompt(String variableType, String variableName, String text) {
        this.variableType = VariableType.valueOf(variableType);
        this.variableName = variableName;
        this.text = text;
        this.isOptional = false;
    }

    public Prompt() {}

    public String getVariableType() {
        return variableType.toString();
    }

    public String getVariableName() {
        return variableName;
    }

    public String getText() {
        return text;
    }

    public boolean isOptional() {
        return isOptional;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setIsOptional(boolean isOptional) {
        isOptional = isOptional;
    }

    @SuppressWarnings("unchecked")
    public <T> T getAnswerAsVariableType() {
        switch(variableType) {
            case INTEGER:
                return (T) Integer.valueOf(answer);
            case DOUBLE:
                return (T) Double.valueOf(answer);
            case BOOLEAN:
                throw new IllegalArgumentException("Please use BooleanPrompt if you want to return a boolean value " +
                        "from a Prompt object.");
            default:
                return (T) answer;
        }
    }

    public String getAnswer() {
        return answer;
    }


    public void setAnswer(String answer) {
        this.answer = answer;
    }



    @Override
    public String toString() {
        return text;
    }

    public Map<?, ?> getOptionsAndValues() {
        return new HashMap<String, String>();
    }


}
