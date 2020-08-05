package CommandLineReader;

import java.util.HashMap;
import java.util.Map;

public class BooleanPrompt extends Prompt {

    private final Map<String, Boolean> optionsAndValues = new HashMap<String, Boolean>() {{
            put("yes", true);
            put("no", false);
    }};


    public BooleanPrompt(String variableName, String prompt, boolean isOptional) {
        super("boolean", variableName, prompt, isOptional);
    }

    public BooleanPrompt(String variableName, String text) {
        super("boolean", variableName, text);
    }

    public BooleanPrompt() {}

    @Override
    public Map<String, Boolean> getOptionsAndValues() {
        return optionsAndValues;
    }
}
