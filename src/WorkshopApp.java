import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class WorkshopApp {
    HttpHeaders inputHeaders = new HttpHeaders();

    public void writeStuff(OutputStream out) throws Exception {
        var stream = new PrintStream(out);

        stream.print('{');
        for (var header: inputHeaders.getHeaders()) {
            stream.printf("\"%s\":\"%s\",", header.key(), header.value());
        }
        stream.print("\"\":null}");
    }

    public void writeOutputHeaders(OutputStream out, int contentLength) throws Exception {
        String template = """
                HTTP/1.1 200 OK\r
                Content-Type: text/plain\r
                Content-Length: %d\r
                \r
                """;

        var outputStream = new PrintStream(out);
        outputStream.printf(template, contentLength);
    }

    public void readInputHeaders(InputStream in) throws Exception {
        var lineReader = new LineReader(in);
        String line = lineReader.getLine();
        System.out.printf("First line of HTTP call:\n%s\n", line);

        for (
                line = lineReader.getLine();
                !lineReader.eof() && !line.isEmpty();
                line = lineReader.getLine()
        ) {
            var parts = line.split(": ");
            if (parts.length != 2) {
                throw new Exception("Too many parts to HTTP header: " + line);
            }
            inputHeaders.addHeader(parts[0], parts[1]);
        }
    }

    public static void main(String[] args) throws Exception {
        var app = new WorkshopApp();
        System.out.println("Listening on: http://localhost:8080");
        try (
                var server = new ServerSocket(8080);
                Socket socket = server.accept();
                var outputStream = socket.getOutputStream();
                var inputStream = socket.getInputStream();
                var buffer = new ByteArrayOutputStream()
        ) {
            app.readInputHeaders(inputStream);
            app.writeStuff(buffer);
            app.writeOutputHeaders(outputStream, buffer.size());
            outputStream.write(buffer.toByteArray());
        }
        System.out.println("My job is done.");
    }
}