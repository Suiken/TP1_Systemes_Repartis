package server;

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
    private String name;
    private String message;
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
//                while ((message = in.readLine()) != null)
//                    System.out.println(name + " : " + message);
                String result = translateMessage();

                out.println(result);
                out.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    String translateMessage() throws Exception{
        File currentClass = new File(getClass().getResource("ServerReception.class").getPath());
        String parentFolderPath = currentClass.getParentFile().getParentFile().getPath();
        File clientFolder = new File(parentFolderPath + "/client");
        clientFolder.mkdir();
        File tmpFile = new File(clientFolder.getPath() + "/tmp.class");
        ByteStream.toFile(socket.getInputStream(), tmpFile);

        message = in.readLine();
        String arguments[] = message.split("&");

        File fileRenamed = new File(clientFolder.getPath() + "/" + arguments[0] + ".class");
        tmpFile.renameTo(fileRenamed);
        tmpFile.delete();

        Class<?> classFile = findClass(arguments[0]);

        String result = executeMethod(classFile, arguments[1], arguments[2].split(","));

        fileRenamed.delete();

        return result;
    }

    Class<?> findClass(String className) throws Exception{
        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] { new File(".").toURI().toURL() });
        Class<?> classOfFile = Class.forName("client." + className, true, classLoader);

        return classOfFile;
    }

    String executeMethod(Class<?> classOfFile, String methodName, String[] parameters) throws Exception{
        Constructor<?> constructorOfFile = classOfFile.getConstructor();
        Object object = constructorOfFile.newInstance();

        Method methods[] = classOfFile.getMethods();
        for(Method method : methods){
            if(method.getName().equals(methodName))
                return method.invoke(object, parameters).toString();
        }
        return "Méthode non trouvée : " + methodName;
    }
}
