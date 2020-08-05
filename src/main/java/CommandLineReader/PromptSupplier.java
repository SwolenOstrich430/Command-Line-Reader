package CommandLineReader;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class PromptSupplier {

    private static final Map<PromptType, Supplier<Prompt>> PROMPT_SUPPLIER;

    static {
        final Map<PromptType, Supplier<Prompt>> prompts = new HashMap<>();

        prompts.put(PromptType.CLASSIC, Prompt::new);
        prompts.put(PromptType.MULTIPLE_CHOICE, MultiChoicePrompt::new);
        prompts.put(PromptType.BOOLEAN, BooleanPrompt::new);

        PROMPT_SUPPLIER = Collections.unmodifiableMap(prompts);
    }

    public Prompt supplyPrompt(PromptType promptType) {
        Prompt prompt = getPrompt(promptType);
        return prompt;
    }

    public Optional<Prompt> supplyPrompt(PromptType promptType, String promptAsJson) {
        Prompt prompt = getPrompt(promptType);

        try {
            prompt = new ObjectMapper().readValue(promptAsJson, prompt.getClass());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("Could not create prompt of prompt type: " + promptType.toString());
            return Optional.empty();
        }

        return Optional.of(prompt);
    }

    private Prompt getPrompt(PromptType promptType) {
        Supplier<Prompt> potentialPrompt = PROMPT_SUPPLIER.get(promptType);

        if(potentialPrompt.get() == null) {
            throw new IllegalArgumentException("Invalid prompt type: " + promptType.toString());
        }

        return potentialPrompt.get();
    }
}
