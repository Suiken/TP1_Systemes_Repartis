package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Dimitri on 16/05/2016.
 */
public class ServerConnection implements Runnable{

    private ServerSocket serverSocket = null;
    private Socket socket = null;
    public Thread thread;

    public ServerConnection(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }

    public void run() {
        try {
            while (true) {
                socket = serverSocket.accept();
                thread = new Thread(new ServerAuthentification(socket));
                thread.start();
            }
        } catch (Exception e) {
            System.err.println("Erreur serveur.");
        }
    }
}
