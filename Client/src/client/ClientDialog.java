package client;

import java.io.*;
import java.net.Socket;

/**
 * Created by Dimitri on 16/05/2016.
 */
public class ClientDialog implements Runnable {

    private Socket socket;
    private OutputStream outputStream;
    private BufferedReader in;
    private Thread emissionThread, receptionThread;

    public ClientDialog(Socket socket){
        this.socket = socket;
    }

    public void run() {
        try {
            outputStream = socket.getOutputStream();
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            receptionThread = new Thread(new ClientReception(in));
            receptionThread.start();

            emissionThread = new Thread(new ClientEmission(outputStream));
            emissionThread.start();
        } catch (IOException e) {
            System.err.println("Le serveur distant s'est déconnecté !");
        }
    }

}
