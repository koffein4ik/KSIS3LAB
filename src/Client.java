import java.io.*;
import java.net.Socket;

public class Client {

    private static Socket clientSocket;
    private static BufferedReader reader;
    private static BufferedReader in;
    private static BufferedWriter out;

    public static void main(String[] args) {
        try {
            try {
                clientSocket = new Socket("localhost", 7);
                reader = new BufferedReader(new InputStreamReader(System.in));
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));


                while (true) {
                    System.out.println("Enter your message:");
                    String word = reader.readLine();

                    out.write(word + "\n");
                    out.flush();
                    if (word.equals("quit"))
                    {
                        break;
                    }
                    String serverWord = in.readLine();
                    System.out.println(serverWord);
                }
            } finally {
                System.out.println("Client closed the connection");
                clientSocket.close();
                in.close();
                out.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }

    }
}