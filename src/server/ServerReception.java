package server;

import common.ByteStream;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.Socket;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by Dimitri on 16/05/2016.
 */
public class ServerReception implements Runnable {
    private Socket socket;
    private String message;
    private String name;
    private PrintWriter out;
    private BufferedReader in;

    public ServerReception(Socket socket, String name){
        try {
            this.socket = socket;
            this.name = name;
            out = new PrintWriter(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void run() {
        while(true){
            try {
                String result = translateMessage(socket);
                System.out.println("Réponse à envoyer : " + result);
                sendMessage("Réponse : " + result, socket);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void sendMessage(String message, Socket socket) throws Exception{
        out = new PrintWriter(socket.getOutputStream());
        out.println(message);
        out.flush();
    }

    File receiveFile(Socket socket) throws Exception{
        File tmpFile = new File("Temp.class");
        ByteStream.toFile(socket.getInputStream(), tmpFile);
        return tmpFile;
    }

    String translateMessage(Socket socket) throws Exception{
        File tmpFile = receiveFile(socket);
        String message = in.readLine();
        String messages[] = message.split("&");
        renameFile(tmpFile, messages[0]);
        Class<?> classFile = findClass(messages[0]);
        return executeMethod(classFile, messages[1], messages[2].split(","));
    }

    File renameFile(File file, String newName){
        File resultFile = new File(newName + ".class");
        file.renameTo(resultFile);
        file.delete();
        return resultFile;
    }

    Class<?> findClass(String className) throws Exception{
        File root = new File(".");
        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] { root.toURI().toURL() });
        Class<?> classOfFile = Class.forName(className, true, classLoader);
        return classOfFile;
    }

    String executeMethod(Class<?> classOfFile, String methodName, String[] parameters) throws Exception{
        Constructor<?> constructorOfFile = classOfFile.getConstructor();
        Object object = constructorOfFile.newInstance(new Object[] { });

        Method methods[] = classOfFile.getMethods();
        Object result = new Object();
        for(Method m : methods){
            if(m.getName().equals(methodName)){
                result = m.invoke(object, parameters);
            }
        }
        return result.toString();
    }
}
