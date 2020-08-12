package CommandLineReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Scanner;


public class Form {
    private List<Prompt> prompts;
    private Scanner scnr;

    public Form(List<Prompt> prompts, String filePath) throws FileNotFoundException {
        this.prompts = prompts;
        scnr = new Scanner(new FileReader(filePath));
    }

    public Form(List<Prompt> prompts) {
        this.prompts = prompts;
        scnr = new Scanner(System.in);
    }

    public List<Prompt> getPrompts() {
        return prompts;
    }

    public void askPromptsAndSetAnswers() {
        for(Prompt currPrompt : prompts) {
            System.out.println(currPrompt.toString());
            currPrompt.setAnswer(scnr.nextLine());
        }
    }
}
