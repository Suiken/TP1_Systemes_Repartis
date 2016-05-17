package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class Client {

    public static void main(String[] zero){

        Socket socket;
        try {

            socket = new Socket("localhost",2009);

            //Reception message
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String message = in.readLine();
            System.out.println(message);

            //envoie message
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            out.println("Calc&add&3,5");
            out.flush();

            socket.close();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}
