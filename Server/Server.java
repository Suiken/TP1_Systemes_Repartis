package server;

import java.io.IOException;
import java.net.*;

public class Server {
    private static ServerSocket socket;
    private static int PORT = 2000;

    public static void main(String[] args){
        try {
            socket = new ServerSocket(PORT, 5);
            Thread serverThread = new Thread(new ServerConnection(socket));
            serverThread.start();

            System.out.println("Serveur en écoute sur le port " + PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}