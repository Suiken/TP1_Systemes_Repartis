package server;

import common.ByteStream;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

class ServerThread implements Runnable {

    private ServerSocket socketServer;
    private Socket socket;
    private HashMap<Socket, Integer> clientMap = new HashMap<>();
    private int id = 0;

    public ServerThread(ServerSocket serverSocket){
        socketServer = serverSocket;
    }

    public void run() {
        try {
            while(true){
                socket = socketServer.accept();
                clientMap.put(socket, id);

                System.out.println("Le client numéro " + id + " est connecté !");

                // Envoi message
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                out.println("Votre ID est " + id);
                out.flush();

                // Reception message
                InputStream socketInputStream = socket.getInputStream();
                String file = ByteStream.toString(socketInputStream);

                BufferedReader in = new BufferedReader(new InputStreamReader(socketInputStream));
                String message = in.readLine();

                System.out.println("Message reçu de " + clientMap.get(socket) + " : " +message);
                System.out.println("Fichier recu : " + file);
                ++id;

                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

