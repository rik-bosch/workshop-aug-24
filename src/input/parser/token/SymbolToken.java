package input.parser.token;

public record SymbolToken(byte value) implements Token {
    @Override
    public String toString() {
        return "SymbolToken[value='" + (char)value() + "']";
    }
}
