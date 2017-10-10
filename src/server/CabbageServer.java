package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// imports as needed
public class CabbageServer {
/* 
 * code here please 
 */
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Date date = new Date();

    private ServerSocket server;
    private Socket connection;
    private int messagenum;
    private int portNum = 8081;
    public static ExecutorService threadExecutor = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        if(args.length > 0){
            (new CabbageServer(Integer.parseInt(args[0]))).runServer();
        }else{
            (new CabbageServer(8081)).runServer();
        }
    }
    public CabbageServer(int portNum){
        this.portNum = portNum;
    }
    public void talkToClient(final Socket connection){
        threadExecutor.execute( new Runnable () {
            public void run(){
                ObjectOutputStream output = null;
                ObjectInputStream input = null;
                String message = "";
                System.out.println("Got a connection");
                try {
                    SocketAddress remoteAddress = connection.getRemoteSocketAddress();
                    String remote = remoteAddress.toString();
                    output = new ObjectOutputStream (connection.getOutputStream());
                    input = new ObjectInputStream( connection.getInputStream());
                    do {
                        message = (String) input.readObject();
                        System.out.println("From:" + remote + ">"+message);
                        if(message == null || message.isEmpty()) {
                            message = null;
                        }
                        else {
                            message = messagenum++ + " Output> " + message;
                        }
                        output.writeObject(message);
                        output.flush();
                    } while (message != null);
                    System.out.println(remote + " disconnected via request");
                } catch (IOException exception) {
                    System.err.println(exception.getMessage());
                    exception.printStackTrace();
                }catch (ClassNotFoundException exception) {
                    System.out.println(exception.getMessage());
                    exception.printStackTrace();
                }
                finally {
                    try{if(input != null){input.close();}}catch(IOException ex){
                        System.out.println(ex.getMessage());}
                    try{if(output != null){output.flush(); output.close();}}catch(IOException ex){
                        System.out.println(ex.getMessage());}
                    try{if(connection != null){connection.close();}}catch(IOException ex){
                        System.out.println(ex.getMessage());}
                }
            }
        });
    }
    public void runServer(){
        try {
            server = new ServerSocket(portNum);
        }catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("Sheldon McGrath " + dateFormat.format(date));
        System.out.println("Listenting for connections...");
        while(true){
            try{
                connection = server.accept();
                talkToClient(connection);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }
}
