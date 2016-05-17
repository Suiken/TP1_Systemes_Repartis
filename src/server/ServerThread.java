package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String message = in.readLine();
                System.out.println("Message reçu de " + clientMap.get(socket) + " : " +message);

                ++id;

                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

