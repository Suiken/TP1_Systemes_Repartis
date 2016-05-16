package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class ServerThread implements Runnable {

    private ServerSocket serverSocket;
    private Socket socket;

    public ServerThread(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void run() {
        try {
            while (true) {
                socket = serverSocket.accept();
                System.out.println("Client connected");
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

