package input.parser.token;

public sealed interface Token permits EofToken, SymbolToken, StringToken, NumberToken {
}
