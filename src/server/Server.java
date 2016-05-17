package server;

import java.io.IOException;
import java.net.*;

public class Server {
    private static ServerSocket socket;
    private static int PORT = 1111;

    public static void main(String[] zero){
        try {
            socket = new ServerSocket(PORT);
            Thread t = new Thread(new ServerThread(socket));
            t.start();
            System.out.println("Serveur démarré !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}