package CommandLineReader;

import java.util.HashMap;
import java.util.Map;

public class BooleanPrompt extends Prompt {

    private final Map<String, Boolean> optionsAndValues = new HashMap<String, Boolean>() {{
            put("yes", true);
            put("no", false);
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
        return getText() + " Answer 'yes' or 'no'.";
    }


    @Override
    public Map<String, Boolean> getOptionsAndValues() {
        return optionsAndValues;
    }
}
