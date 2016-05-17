package server;

import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by Dimitri on 16/05/2016.
 */
public class ServerEmission implements Runnable {

    private PrintWriter out;
    private String message;
    private Scanner scanner;

    public ServerEmission(PrintWriter out) {
        this.out = out;
    }

    public void run() {
        scanner = new Scanner(System.in);

        while(true){
            message = scanner.nextLine();
            out.println(message);
            out.flush();
        }
    }
}
