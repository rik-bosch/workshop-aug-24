import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class WorkshopApp {
    private final byte[] message = {
            0x48, 0x61, 0x6c, 0x6c,
            0x6f, 0x20, 0x77, 0x65,
            0x72, 0x65, 0x6c, 0x64,
            0x21, 0x0a
    };

    public void helloWorld(OutputStream out) {
        try {
            for (int i = 0; i < message.length; ++i) {
                out.write(message[i]);
            }
        }
        catch (IOException e) {
            System.err.println("Error writing to output.");
            System.err.println(e.getMessage());
        }
    }

    public void writeHttpHeaders(OutputStream out) {
        String headers = """
                HTTP/1.1 200 OK
                Content-Type: text/plain
                
                """;

        try {
            out.write(headers.getBytes());
        }
        catch (IOException e) {
            System.err.println("Error writing bytes.");
            System.err.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        var app = new WorkshopApp();
        try (
                var server = new ServerSocket(8080);
                Socket socket = server.accept();
                OutputStream outputStream = socket.getOutputStream();
        ) {
            System.out.println("Listening on: http://localhost:8080");
            app.writeHttpHeaders(outputStream);
            app.helloWorld(outputStream);
        }
        catch (IOException e) {
            System.err.println("Error opening server.");
            System.err.println(e.getMessage());
        }
        finally {
            System.out.println("My job is done.");
        }
    }
}