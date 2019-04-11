import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static Socket clientSocket;
    private static ServerSocket server;
    private static BufferedReader in;
    private static BufferedWriter out;

    public static void main(String[] args) {
        try {
            try {
                server = new ServerSocket(7);
                clientSocket = server.accept();
                try {
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                    while(true) {
                        String word = in.readLine();
                        if (word.equals("quit")) {
                            break;
                        }
                        System.out.println(word);
                        out.write(word + "\n");
                        out.flush();
                    }
                }
                catch(Exception ex)
                {
                    System.out.println(ex.toString());
                }
            } finally {
                clientSocket.close();
                in.close();
                out.close();
                System.out.println("Server closed");
                server.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}