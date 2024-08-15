import java.io.FileInputStream;
import java.io.InputStream;

public class LineReader {
    private InputStream in;
    private int nextChar;

    public LineReader(InputStream input) throws Exception {
        this.in = input;
        this.nextChar = in.read();
    }

    public boolean eof() {
        return nextChar == -1;
    }

    public boolean eol() throws Exception {
        switch (nextChar) {
            case 0x0D:
                nextChar = in.read();
            case 0x0A:
                nextChar = in.read();
                return true;
            default:
                return false;
        }
    }

    public String getLine() throws Exception {
        var line = new StringBuilder();
        for (;!eof() && !eol(); nextChar = in.read()) {
            if (isInAsciiRange(nextChar)) {
                line.append((char) nextChar);
            }
        }
        return line.toString();
    }

    private boolean isInAsciiRange(int c) {
        return 0x20 <= c && c < 0x80;
    }

    static public void main(String[] args) throws Exception {
        var input = new FileInputStream("dream.txt");
        var reader = new LineReader(input);
        int lineNo = 1;
        while (!reader.eof()) {
            System.out.printf("%d: %s\n", lineNo++, reader.getLine());
        }
    }
}
