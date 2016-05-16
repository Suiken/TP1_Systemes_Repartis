package server;

import java.io.IOException;

import java.net.ServerSocket;


public class Server {


    public static void main(String[] zero) {

        ServerSocket socket;

        try {

            socket = new ServerSocket(2009);

            Thread t = new Thread(new ServerThread(socket));

            t.start();

            System.out.println("Server ready");

            socket.close();



        } catch (IOException e) {



            e.printStackTrace();

        }

    }


}