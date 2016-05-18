package client;


import client.common.ByteStream;

import java.io.File;
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
                "1 - Demander une opération\n" +
                "h - Liste des commandes\n" +
                "q - Quitter\n";

        System.out.println("\n" + help);

        String input = "";
        while (input != "q" || !scanner.hasNextLine()){
            input = scanner.nextLine();

            switch (input){
                case "1" :
                    sendOperation();
                    System.out.println(help);
                    break;
                case "h" :
                    System.out.println(help);
                    break;
                case "q" :
                    System.exit(0);
                default :
                    sendOperation();
                    break;
            }
        }
    }

    public void sendOperation(){
        try {
            String message = getLine("Saisir une opération :");

            AbstractMap.SimpleEntry<String, String> entry = translate(message);
            if (entry != null){
                ByteStream.toStream(outputStream, new File(entry.getKey()));

                out.println(entry.getValue());
                out.flush();
                System.out.println("(envoyé)\n");
            }
            else {
                //out.println(message);
                //out.flush();
                System.out.println("'" + message + "' n'est pas une opération.\n");
            }
        } catch (Exception e) {
            System.err.println("Le serveur ne répond plus.");
            System.exit(0);
        }
    }

    public AbstractMap.SimpleEntry<String, String> translate(String input) {
        Pattern pattern = Pattern.compile(MathRegex.OPERATION.toString());
        Matcher matcher = pattern.matcher(input);
        AbstractMap.SimpleEntry<String, String> entry;

        if (!matcher.find() || (entry = translateIntoOperation(input)) == null)
            return null;

        return entry;
    }

    public AbstractMap.SimpleEntry<String, String> translateIntoOperation(String input){
        MathRegex operation = getOperation(input);
        if (operation.equals(MathRegex.OPERATION))
            return null;

        AbstractMap.SimpleEntry<Integer, Integer> values = getParameters(input, operation);

        String classPath = Calc.class.getResource("Calc.class").getPath();
        String message = "Calc&" + operation.getMethodName() + "&" + values.getKey() + "," + values.getValue();

        return new AbstractMap.SimpleEntry<>(classPath, message);
    }

    public MathRegex getOperation(String input){
        Matcher matcher;
        for (MathRegex op : MathRegex.values()){
            matcher = Pattern.compile(op.toString()).matcher(input);
            if (matcher.find() && !op.equals(MathRegex.OPERATION)) {
                return op;
            }
        }
        return MathRegex.OPERATION;
    }

    public AbstractMap.SimpleEntry<Integer, Integer> getParameters(String input, MathRegex operation){
        input = input.replaceAll("\\s+","");
        String[] parameters = input.split(operation.getOperator());
        int a = Integer.parseInt(parameters[0]);
        int b = Integer.parseInt(parameters[1]);

        return new AbstractMap.SimpleEntry<>(a, b);
    }

    public String getLine(String message){
        System.out.println(message);
        while(!scanner.hasNextLine()){}

        return scanner.nextLine();
    }
}
