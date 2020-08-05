package CommandLineReader;

public enum PromptType {

    CLASSIC("classic"),
    BOOLEAN("boolean"),
    MULTIPLE_CHOICE("multiple-choice")
    ;

    private String type;

    PromptType(String type) {
        this.type = type;
    }
}
