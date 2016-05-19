package server;

import server.common.ByteStream;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.Socket;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;

/**
 * Created by Dimitri on 16/05/2016.
 */
public class ServerMethodExecution implements Runnable {
    private Socket socket;
    private String message;
    private PrintWriter out;
    private BufferedReader in;
    //private String name = "";
    Class<?> classOfFile;
    String methodName;
    String[] parameters;

    public ServerMethodExecution(Socket socket, Class<?> classOfFile, String methodName, String[] parameters){
        try {
            this.socket = socket;
            //this.name = name;
            this.classOfFile = classOfFile;
            this.methodName = methodName;
            out = new PrintWriter(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void run() {
        while(true){
            try {
//                while ((message = in.readLine()) != null)
//                    System.out.println(name + " : " + message);
                Constructor<?> constructorOfFile = classOfFile.getConstructor();
                Object object = constructorOfFile.newInstance();

                Method methods[] = classOfFile.getMethods();
                for(Method method : methods){
                    if(method.getName().equals(methodName)) {
                        out.println(method.invoke(object, parameters).toString());
                        out.flush();
                        return;
                    }
                }
                out.println("Méthode non trouvée : " + methodName);
                out.flush();
            } catch (Exception e) {
                return;
            }
        }
    }

    void executeMethod(Class<?> classOfFile, String methodName, String[] parameters) throws Exception{
        Constructor<?> constructorOfFile = classOfFile.getConstructor();
        Object object = constructorOfFile.newInstance();

        Method methods[] = classOfFile.getMethods();
        for(Method method : methods){
            if(method.getName().equals(methodName)) {
                out.println(method.invoke(object, parameters).toString());
                out.flush();
                return;
            }
        }
        out.println("Méthode non trouvée : " + methodName);
        out.flush();
    }
}
