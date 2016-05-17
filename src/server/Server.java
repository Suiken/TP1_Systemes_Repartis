package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;

public class Server {

    public static void main(String[] zero){

        ServerSocket socket;
        PrintWriter out;

        try {
            socket = new ServerSocket(2009);
            Thread t = new Thread(new ServerThread(socket));
            t.start();
            System.out.println("Mes employeurs sont prêts !");

        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}