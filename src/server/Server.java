package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        ServerSocket serverSocket;
        Socket socket;
        PrintWriter out;

        try {
            serverSocket = new ServerSocket(1111);
            System.out.println("Le serveur est à l'écoute du port " + serverSocket.getLocalPort());

            socket = serverSocket.accept();
            System.out.println("Un client s'est connecté");
            out = new PrintWriter(socket.getOutputStream());
            out.println("Vous êtes connecté !");
            out.flush();

            socket.close();
            serverSocket.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}