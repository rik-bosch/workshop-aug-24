package input.parser.token;

public record SymbolToken(char value) implements Token {
    @Override
    public String toString() {
        return "SymbolToken[value='" + value() + "']";
    }
}
