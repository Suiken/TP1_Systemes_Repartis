package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Dimitri on 16/05/2016.
 */
public class ServerDialog implements Runnable {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String name;
    private Thread emissionThread, receptionThread;

    public ServerDialog(Socket socket, String name){
        this.socket = socket;
        this.name = name;
    }

    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            emissionThread = new Thread(new ServerReception(in, name));
            emissionThread.start();

            receptionThread = new Thread(new ServerEmission(out));
            receptionThread.start();

        } catch (IOException e) {
            System.err.println("Déconnexion de " + name + ".");
        }
    }
}
