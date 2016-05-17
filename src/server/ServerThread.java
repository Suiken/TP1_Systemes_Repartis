package server;

import common.ByteStream;

import java.io.*;
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
                InputStream socketInputStream = socket.getInputStream();
                String message = ByteStream.toString(socketInputStream);
                //BufferedReader in = new BufferedReader(new InputStreamReader(socketInputStream));
                //String message = in.readLine();
                System.out.println("Message recu " + message);

                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

