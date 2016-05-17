package client;

import java.io.*;
import java.net.*;

public class Client {
    private static Socket socket;
    private static String ADDRESS = "localhost";
    private static int PORT = 2000;
    public static Thread thread;

    public static void main(String[] zero) {
        try {
            socket = new Socket(ADDRESS, PORT);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String message = in.readLine();
            System.out.println(message);

            thread = new Thread(new ClientConnection(socket));
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
