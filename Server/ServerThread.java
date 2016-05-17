package server;

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
//                Socket socket = socketServer.accept();
//                clientMap.put(socket, id);
//
//                System.out.println("Le client numéro " + id + " est connecté !");
//
//                sendOperation("Votre ID est " + id, socket);
//
//                // Reception message
//                String message = receiveMessage(socket);
//                System.out.println("Message reçu de " + clientMap.get(socket) + " : " + message);
//
//                String[] messages = message.split("&");
//                Class<?> classOfFile = findClass(messages[0]);
//
//                Object result = executeMethod(classOfFile, messages[1], messages[2].split(","));
//                System.out.println("Réponse à envoyer : " + result.toString());
//
//                ++id;

                // Envoi message
//                PrintWriter res = new PrintWriter(socket.getOutputStream());
//                res.println(result.toString());
//                res.flush();


//                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    void sendOperation(String message, Socket socket) throws Exception{
//        PrintWriter out = new PrintWriter(socket.getOutputStream());
//        out.println("Votre ID est " + id);
//        out.flush();
//    }
//
//    String receiveMessage(Socket socket) throws Exception{
//        InputStream socketInputStream = socket.getInputStream();
//
//        File file = new File("Calc.class");
//        ByteStream.toFile(socketInputStream, file);
//
//        BufferedReader in = new BufferedReader(new InputStreamReader(socketInputStream));
//        String message = in.readLine();
//
//        return message;
//
//    }
//
//    Class<?> findClass(String className) throws Exception{
//        File root = new File(".");
//        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] { root.toURI().toURL() });
//        Class<?> classOfFile = Class.forName(className, true, classLoader);
//        return classOfFile;
//    }
//
//    Object executeMethod(Class<?> classOfFile, String methodName, String[] parameters) throws Exception{
//        Constructor<?> constructorOfFile = classOfFile.getConstructor();
//        Object object = constructorOfFile.newInstance(new Object[] { });
//
//        Method methods[] = classOfFile.getMethods();
//        Object result = new Object();
//        for(Method m : methods){
//            if(m.getName().equals(methodName)){
//                result = m.invoke(object, parameters);
//            }
//        }
//        return result;
//    }
}

