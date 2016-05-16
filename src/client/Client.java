package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    public static void main(String[] zero) {
        Socket socket;
        BufferedReader in;

        try {
            socket = new Socket(InetAddress.getLocalHost(), 1111);
            System.out.println("Connected !");

            in = new BufferedReader (new InputStreamReader(socket.getInputStream()));

            String message_distant;
            if ((message_distant = in.readLine()) != null)
                System.out.println(message_distant);

            socket.close();
        }catch (UnknownHostException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}