package CommandLineReader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class PromptMapper {

    private String fileName;
    private PromptSupplier promptSupplier;
    private ObjectMapper objectMapper;

    public PromptMapper() {
        this.promptSupplier = new PromptSupplier();
        this.objectMapper = new ObjectMapper();
    }

    public ObjectNode[] getPromptsAsJson(String fileName) throws IOException, IncorrectPromptFieldException {
        ObjectNode[] promptsAsJson = objectMapper.readValue(Paths.get(fileName).toFile(), ObjectNode[].class);

        for(int i = 0; i < promptsAsJson.length; i++) {
            PromptValidator.validateJsonPrompt(promptsAsJson[i], i);
        }

        return promptsAsJson;
    }

    public List<Prompt> getPrompts(String fileName) throws IOException, IncorrectPromptFieldException {
       ObjectNode[] promptsAsJson = getPromptsAsJson(fileName);
       List prompts = new ArrayList();

       if(promptsAsJson.length == 0) {
            return prompts;
       }

       for(ObjectNode promptAsJson : promptsAsJson) {
           String promptTypeAsString = promptAsJson.get("promptType").asText();
           PromptType promptType = PromptType.valueOf(promptTypeAsString);

           promptAsJson.remove("promptType");
           Optional<Prompt> potentialPrompt = promptSupplier.supplyPrompt(promptType, promptAsJson.toString());

           if(potentialPrompt.isPresent()) {
               prompts.add(potentialPrompt.get());
           }
       }

       return prompts;
    }
}
