package CommandLineReaderTest;


import CommandLineReader.Form;
import CommandLineReader.Prompt;
import CommandLineReader.PromptMapper;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.junit.Rule;
import org.junit.Test;

import java.io.FileReader;
import java.util.List;

import static org.junit.Assert.*;


public class FormTester {
    private final PromptMapper promptMapper = new PromptMapper();


    @Test
    public void CreateFormFromValidPrompts() throws Throwable {
        List<Prompt> prompts = promptMapper.getPrompts("src/main/resources/test-prompts.json");
        Form form = new Form(prompts);

        assertTrue(form.getPrompts().size() == 8);
    }

    @Test
    public void getAnswers() throws Throwable {
        List<Prompt> prompts = promptMapper.getPrompts("src/main/resources/test-prompts.json");
        Form form = new Form(prompts, "src/main/resources/test-prompt-answers.txt");

        form.askPromptsAndSetAnswers();

        List<Prompt> answeredPrompts = form.getPrompts();
        assert(answeredPrompts.get(0).getAnswerAsVariableType() instanceof Boolean);
        assert answeredPrompts.get(1).getAnswerAsVariableType() instanceof Boolean;
        assert answeredPrompts.get(2).getAnswerAsVariableType() instanceof String;
        assert(answeredPrompts.get(3).getAnswerAsVariableType() instanceof Integer);
        assert answeredPrompts.get(4).getAnswerAsVariableType() instanceof Double;
        assert answeredPrompts.get(5).getAnswerAsVariableType() instanceof Integer;
        assert(answeredPrompts.get(6).getAnswerAsVariableType() instanceof Integer);
        assert answeredPrompts.get(7).getAnswerAsVariableType() instanceof Boolean;
    }
}
