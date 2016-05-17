package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

class ServerThread implements Runnable {

    private ServerSocket socketServer;
    private Socket socket;
    private int nbClient = 1;

    public ServerThread(ServerSocket serverSocket){
        socketServer = serverSocket;
    }

    public void run() {
        try {
            while(true){
                socket = socketServer.accept();
                System.out.println("Le client numéro " + nbClient + " est connecté !");
                nbClient++;

                // Envoie message
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                out.println("Votre ID est " + nbClient);
                out.flush();

                // Reception message
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String message = in.readLine();
                System.out.println("Message reçu : " + message);

                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

