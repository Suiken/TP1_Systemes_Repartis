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
public class ClientConnection implements Runnable {

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private Scanner scanner;
    public static Thread thread;
    public static String name;

    public ClientConnection(Socket socket){
        this.socket = socket;
    }

    public void run() {
        try {
            out = new PrintWriter(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            scanner = new Scanner(System.in);

            name = scanner.nextLine();
            out.println(name);
            out.flush();

            thread = new Thread(new ClientDialog(socket));
            thread.start();
        } catch (IOException e) {
            System.err.println("Pas de réponse du serveur.");
        }
    }
}
