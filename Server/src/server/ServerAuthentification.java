package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Dimitri on 16/05/2016.
 */
public class ServerAuthentification implements Runnable {

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String name;
    public Thread thread;

    public ServerAuthentification(Socket socket){
        this.socket = socket;
    }

    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            out.println("Entrer un nom :");
            out.flush();
            name = in.readLine();
            System.out.println(name + " est connecté.");
            out.flush();

            thread = new Thread(new ServerDialog(socket, name));
            thread.start();
        } catch (IOException e) {
            System.err.println(name + " ne répond pas.");
        }
    }
}
