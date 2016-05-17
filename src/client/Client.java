package client;

import common.ByteStream;

import java.io.*;
import java.net.*;
import java.util.AbstractMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client {
    private static Socket socket;
    private static String ADDRESS = "localhost";
    private static int PORT = 2009;

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
            ByteStream.toStream(socket.getOutputStream(), new File("/home/suiken/Bureau/Calc.class"));

            String message = getLine("Saisir un message :");
            PrintWriter out = new PrintWriter(socket.getOutputStream());

            AbstractMap.SimpleEntry<String, String> entry = translateIntoAddition(message);
            if (entry != null){
                out.println(entry.getKey());
                out.println(entry.getValue());
            }
            else
                out.println(message);

            out.flush();

            System.out.println("(envoyé)\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static AbstractMap.SimpleEntry<String, String> translateIntoAddition(String input) {
        Pattern pattern = Pattern.compile(MathRegex.ADDITION.toString());
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            input = input.replaceAll("\\s+","");

            String[] parameters = input.split("\\+");
            int a = Integer.parseInt(parameters[0]);
            int b = Integer.parseInt(parameters[1]);

//            String classPath = Calc.class.getResource("Calc.class").toString();
            String classPath = "Calc&add&" + a + "," + b;
            String message = "Calc&add&" + a + "," + b;

            return new AbstractMap.SimpleEntry<>(classPath, message);
        }
        return null;
    }

    public static String getLine(String message){
        Scanner scanner = new Scanner(System.in);
        System.out.println(message);
        while(!scanner.hasNextLine()){}

        return scanner.nextLine();
    }
}
