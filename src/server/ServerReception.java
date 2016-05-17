package server;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by Dimitri on 16/05/2016.
 */
public class ServerReception implements Runnable {
    private BufferedReader in;
    private String message;
    private String name;

    public ServerReception(BufferedReader in, String name){
        this.in = in;
        this.name = name;
    }

    public void run() {
        while(true){
            try {
                message = in.readLine();
                System.out.println(name + " : " + message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
