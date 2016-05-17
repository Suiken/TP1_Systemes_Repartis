package server;

import java.io.*;
import java.net.Socket;

/**
 * Created by Dimitri on 16/05/2016.
 */
public class ServerReception implements Runnable {
    private Socket socket;
    private String message;
    private String name;

    public ServerReception(Socket socket, String name){
        this.socket = socket;
        this.name = name;
    }

    public void run() {
        while(true){
            try {
                InputStream socketInputStream = socket.getInputStream();
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(socketInputStream));

                while ((message = in.readLine()) != null)
                    System.out.println(name + " : " + message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
