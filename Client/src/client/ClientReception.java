package client;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by Dimitri on 16/05/2016.
 */
public class ClientReception implements Runnable {
    private BufferedReader in;
    private String message;

    public ClientReception(BufferedReader in){
        this.in = in;
    }

    public void run() {
        while(true){
            try {
                while ((message = in.readLine()) != null) {
                    System.out.println("SERVEUR : " + message);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
    }
}
