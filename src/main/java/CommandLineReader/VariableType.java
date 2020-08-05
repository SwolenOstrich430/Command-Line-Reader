package CommandLineReader;

public enum VariableType {

    BOOLEAN("boolean"),
    STRING("String"),
    INTEGER("integer"),
    DOUBLE("double")
    ;

    private String variableName;

    VariableType(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public String toString() {
        return this.variableName;
    }
}
