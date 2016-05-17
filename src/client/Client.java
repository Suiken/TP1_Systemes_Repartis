package client;

import common.ByteStream;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    private static Socket socket;
    private static String ADDRESS = "localhost";
    private static int PORT = 2000;

    public static void main(String[] zero){

        try {
            socket = new Socket(ADDRESS, PORT);

            // Reception message
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String message = in.readLine();
            System.out.println(message);

            display();

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void display(){
        String help = "[DISPLAY]\n" +
                "1 - Envoyer un message\n" +
                "h - Liste des commandes\n" +
                "q - Quitter\n";

        System.out.println(help);

        Scanner scanner = new Scanner(System.in);
        String input = "";
        while (input != "q" || !scanner.hasNextLine()){
            input = scanner.nextLine();

            switch (input){
                case "1" :
                    sendMessage();
                    System.out.println(help);
                    break;
                case "h" :
                    System.out.println(help);
                    break;
                case "q" :
                    return;
            }
        }
    }

    public static void sendMessage(){
        try {
            ByteStream.toStream(socket.getOutputStream(), new File(getLine("Saisir le chemin d'un fichier :")));

            String message = getLine("Saisir un message :");

            PrintWriter out = new PrintWriter(socket.getOutputStream());
            out.println(message);
            out.flush();

            System.out.println("(envoyé)\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getLine(String message){
        Scanner scanner = new Scanner(System.in);
        System.out.println(message);
        while(!scanner.hasNextLine()){}
        return scanner.nextLine();
    }
}
