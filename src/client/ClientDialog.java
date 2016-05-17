package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Dimitri on 16/05/2016.
 */
public class ClientDialog implements Runnable {

    private Socket socket;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private Scanner scanner;
    private Thread emissionThread, receptionThread;

    public ClientDialog(Socket socket){
        this.socket = socket;
    }

    public void run() {
        try {
            out = new PrintWriter(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            receptionThread = new Thread(new ClientReception(in));
            receptionThread.start();

            emissionThread = new Thread(new ClientEmission(out));
            emissionThread.start();
        } catch (IOException e) {
            System.err.println("Le serveur distant s'est déconnecté !");
        }
    }

}
