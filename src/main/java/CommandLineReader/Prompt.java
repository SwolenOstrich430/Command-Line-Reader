package CommandLineReader;

import java.util.HashMap;
import java.util.Map;

public class Prompt {

    private VariableType variableType;
    private String variableName;
    private String text;
    private boolean isOptional;


    public Prompt(String variableType, String variableName, String text, boolean isOptional) {
        this.variableType = VariableType.valueOf(variableType);
        this.variableName = variableName;
        this.text = text;
        this.isOptional = isOptional;
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

    public Map<?, ?> getOptionsAndValues() {
        return new HashMap<String, String>();
    }
}
