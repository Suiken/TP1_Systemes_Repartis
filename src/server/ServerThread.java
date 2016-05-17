package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

class ServerThread implements Runnable {

    private ServerSocket socketserver;

    private Socket socket;

    private int nbrclient = 1;

    public ServerThread(ServerSocket s){

        socketserver = s;

    }

    public void run() {

        try {

            while(true){

                socket = socketserver.accept(); // Un client se connecte on l'accepte

                System.out.println("Le client numéro "+nbrclient+ " est connecté !");

                nbrclient++;

                //envoie message
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                out.println("Votre id est " + nbrclient);
                out.flush();

                //Reception message
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String message = in.readLine();
                System.out.println("Message recu " + message);

                socket.close();

            }

        } catch (IOException e) {

            e.printStackTrace();

        }

    }
}

