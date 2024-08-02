import java.io.*;

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

    public static void main(String[] args) {
        var app = new WorkshopApp();
        try (
            var fos = new FileOutputStream("hallo.txt")
        ) {
            app.helloWorld(fos);
        }
        catch (FileNotFoundException e) {
            System.err.println("Error opening file.");
            System.err.println(e.getMessage());
        }
        catch (IOException e) {
            System.err.println("Error closing file.");
            System.err.println(e.getMessage());
        }
    }
}