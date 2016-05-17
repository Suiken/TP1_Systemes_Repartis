package server;

import common.ByteStream;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;

class ServerThread implements Runnable {

    private ServerSocket socketServer;
    private HashMap<Socket, Integer> clientMap = new HashMap<>();
    private int id = 0;

    public ServerThread(ServerSocket serverSocket){
        socketServer = serverSocket;
    }

    public void run() {
        try {
            while(true){
                Socket socket = socketServer.accept();
                clientMap.put(socket, id);

                System.out.println("Le client numéro " + id + " est connecté !");

                // Envoi message
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                out.println("Votre ID est " + id);
                out.flush();

                // Reception message
                InputStream socketInputStream = socket.getInputStream();

                File file = new File("Calc.class");
                ByteStream.toFile(socketInputStream, file);

                BufferedReader in = new BufferedReader(new InputStreamReader(socketInputStream));
                String message = in.readLine();

                System.out.println("Message reçu de " + clientMap.get(socket) + " : " + message);

                String[] messages = message.split("&");

                File root = new File(".");
                URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] { root.toURI().toURL() });
                Class<?> classOfFile = Class.forName(messages[0], true, classLoader);
                Constructor<?> constructorOfFile = classOfFile.getConstructor();
                Object object = constructorOfFile.newInstance(new Object[] { });
                Method methods[] = classOfFile.getMethods();
                Object result = new Object();
                for(Method m : methods){
                    if(m.getName().equals(messages[1])){
                        result = m.invoke(object, messages[2].split(","));
                    }
                }


                System.out.println("Fichier recu : " + file);
                System.out.println("Réponse à envoyer : " + result.toString());
                ++id;

                // Envoi message
//                PrintWriter res = new PrintWriter(socket.getOutputStream());
//                res.println(result.toString());
//                res.flush();


                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

