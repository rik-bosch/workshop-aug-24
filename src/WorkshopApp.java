import input.HttpHeaders;
import input.LineReader;
import models.StudentList;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class WorkshopApp {
    private final HttpHeaders inputHeaders = new HttpHeaders();

    public void writeStuff(OutputStream out) {
        var stream = new PrintStream(out);
        var studentList = StudentList.mock();
        studentList.writeJson(stream);
    }

    public void writeOutputHeaders(OutputStream out, int contentLength) throws Exception {
        String template = """
                HTTP/1.1 200 OK\r
                Content-Type: application/json\r
                Content-Length: %d\r
                \r
                """;

        byte[] headers = template.formatted(contentLength).getBytes();
        out.write(headers);
    }

    public void readInputHeaders(InputStream in) throws Exception {
        var lineReader = new LineReader(in);
        String line = lineReader.getLine();
        System.out.printf("First line of HTTP call:\n%s\n", line);

        for (
                line = lineReader.getLine();
                !line.isEmpty();
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