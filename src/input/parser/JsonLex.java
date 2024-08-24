package input.parser;

import input.parser.token.*;

public class JsonLex {
    private final byte[] data;
    private int index = 0;
    private Token buffer = null;

    public JsonLex(byte[] data) {
        this.data = data;
        skipWhitespaces();
    }

    public int getOffset() {
        return index;
    }

    public Token getToken() {
        if (buffer != null) {
            var t = buffer;
            buffer = null;
            return t;
        }
        else if (index >= data.length) {
            return new EofToken();
        }
        else if (isDigit(data[index])) {
            return getNumberToken();
        }
        else if (data[index] == '"') {
            return getStringToken();
        }
        else {
            return getSymbolToken();
        }
    }

    public void putBack(Token token) {
        buffer = token;
    }

    private void skipWhitespaces() {
        while (index < data.length && data[index] <= 0x20) {
            index++;
        }
    }

    private Token getSymbolToken() {
        byte value = data[index];
        index++;
        skipWhitespaces();
        return new SymbolToken((char)value);
    }

    private Token getNumberToken() {
        int value = data[index] - '0';
        for (index++; index < data.length && isDigit(data[index]); index++) {
            value = (value << 3) + (value << 1) + (data[index] - '0');
        }
        skipWhitespaces();
        return new NumberToken(value);
    }

    private Token getStringToken() {
        var value = new StringBuilder(16);
        index++;
        value.append((char)data[index]);
        for (index++; index < data.length && data[index] != '"'; index++) {
            value.append((char)data[index]);
        }
        index++;
        skipWhitespaces();
        return new StringToken(value.toString());
    }

    private boolean isDigit(byte b) {
        return '0' <= b && b <= '9';
    }
}
