package client;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.AbstractMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Dimitri on 16/05/2016.
 */
public class ClientEmission implements Runnable {
    private OutputStream outputStream;
    private PrintWriter out;
    private Scanner scanner;

    public ClientEmission(OutputStream outputStream) {
        this.outputStream = outputStream;
        this.out = new PrintWriter(outputStream);
    }

    public void run() {
        scanner = new Scanner(System.in);
        display();
    }

    public void display(){
        String help = "[DISPLAY]\n" +
                "1 - Envoyer un message\n" +
                "h - Liste des commandes\n" +
                "q - Quitter\n";

        System.out.println(help);

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

    public void sendMessage(){
        try {
            String message = getLine("Saisir un message :");

            AbstractMap.SimpleEntry<String, String> entry = translateIntoAddition(message);
            if (entry != null){
                out.println(entry.getKey());
                out.flush();

                out.println(entry.getValue());
                out.flush();
            }
            else {
                out.println(message);
                out.flush();
            }
            System.out.println("(envoyé)\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AbstractMap.SimpleEntry<String, String> translateIntoAddition(String input) {
        Pattern pattern = Pattern.compile(MathRegex.ADDITION.toString());
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            input = input.replaceAll("\\s+","");

            String[] parameters = input.split("\\+");
            int a = Integer.parseInt(parameters[0]);
            int b = Integer.parseInt(parameters[1]);

            String classPath = Calc.class.getResource("Calc.class").toString();
            String message = "Calc&add&" + a + "," + b;

            return new AbstractMap.SimpleEntry<>(classPath, message);
        }
        return null;
    }

    public String getLine(String message){
        System.out.println(message);
        while(!scanner.hasNextLine()){}

        return scanner.nextLine();
    }
}
