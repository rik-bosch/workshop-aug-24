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

    public void helloWorld(OutputStream out) throws Exception {
            for (int i = 0; i < message.length; ++i) {
                out.write(message[i]);
            }
    }

    public void writeHttpHeaders(OutputStream out) throws Exception {
        String headers = """
                HTTP/1.1 200 OK
                Content-Type: text/plain
                
                """;

        out.write(headers.getBytes());
    }

    public int hash(byte[] data) {
        int hash = 5381;
        for (byte b: data) {
            hash = (hash << 5) + hash + b;
        }
        return hash;
    }

    public static void webMain() throws Exception {
        var app = new WorkshopApp();
        System.out.println("Listening on: http://localhost:8080");
        try (
                var server = new ServerSocket(8080);
                Socket socket = server.accept();
                OutputStream outputStream = socket.getOutputStream();
        ) {
            app.writeHttpHeaders(outputStream);
            app.helloWorld(outputStream);
        }
        System.out.println("My job is done.");
    }

    public static void main(String[] args) throws Exception {
        var app = new WorkshopApp();
        var input = new FileInputStream("hello.txt");
        var hash = app.hash(input.readAllBytes());

        System.out.println(hash);
    }
}