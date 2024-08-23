package input;

import java.io.FileInputStream;
import java.io.InputStream;

public class LineReader {
    private final InputStream in;
    private int nextChar;

    public LineReader(InputStream input) {
        this.in = input;
    }

    public String getLine() throws Exception {
        var line = new StringBuilder();
        for (nextChar = in.read(); !eof() && !eol(); nextChar = in.read()) {
            if (isInAsciiRange(nextChar)) {
                line.append((char) nextChar);
            }
        }
        return line.toString();
    }

    private boolean eof() {
        return nextChar == -1;
    }

    private boolean eol() throws Exception {
        switch (nextChar) {
            case 0x0D:
                nextChar = in.read();
            case 0x0A:
                return true;
            default:
                return false;
        }
    }

    private boolean isInAsciiRange(int c) {
        return 0x20 <= c && c < 0x80;
    }

    static public void main(String[] args) throws Exception {
        var input = new FileInputStream("test-lines.txt");
        var reader = new LineReader(input);
        int lineNo = 1;
        while (!reader.eof()) {
            System.out.printf("%d: %s\n", lineNo++, reader.getLine());
        }
    }
}
