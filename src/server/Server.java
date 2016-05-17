package server;

import java.io.IOException;
import java.net.*;

public class Server {
    private static ServerSocket socket;
    private static int PORT = 2000;

    public static void main(String[] zero){
        try {
            socket = new ServerSocket(PORT);

            Thread serverThread = new Thread(new ServerThread(socket));
            serverThread.start();

            System.out.println("Serveur démarré !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}