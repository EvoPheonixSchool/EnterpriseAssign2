package server;

import dataaccesslayer.CabbageDaoImpl;
import datatransfer.Message;

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

/**
 * Class handles taking cabbage from clients and inserting them into the database
 * Class provided by Stan Pieda
 * Modified by Sheldon McGrath
 */
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

    /**
     * Main mothod takes a port as a parameter, default is 8081
     * @param args
     */
    public static void main(String[] args) {
        if(args.length > 0){
            (new CabbageServer(Integer.parseInt(args[0]))).runServer();
        }else{
            (new CabbageServer(8081)).runServer();
        }
    }

    /**
     * Constructor takes given port number
     * @param portNum
     */
    public CabbageServer(int portNum){
        this.portNum = portNum;
    }


    /**
     * main logic for server, new thread for each client
     * @param connection
     */
    public void talkToClient(final Socket connection){
        threadExecutor.execute( new Runnable () {
            public void run(){
                ObjectOutputStream output = null;
                ObjectInputStream input = null;
                Message message = null;
                String pass,uuid;

                //String message = "";
                System.out.println("Got a connection");
                try {
                    //creates steam objects for client communication
                    SocketAddress remoteAddress = connection.getRemoteSocketAddress();
                    String remote = remoteAddress.toString();
                    output = new ObjectOutputStream (connection.getOutputStream());
                    input = new ObjectInputStream( connection.getInputStream());
                    do{
                        //read message from client
                        message = (Message) input.readObject();
                       // System.out.println(message.getCommand());
                        //checks for disconnect form client
                    if(message.getCommand().equalsIgnoreCase("disconnect")){
                        System.out.println("Disconnecting from client");
                        message.setCommand("disconnect");
                    }else {
                        //insert cabbage into database
                        CabbageDaoImpl cabDao = new CabbageDaoImpl();
                        // do {
                        //System.out.println(message.getCabbage().getAlpha());
                        pass = cabDao.insertCabbage(message.getCabbage());
                        uuid = message.getCabbage().getUUID();
                        /*message = (String) input.readObject();
                        System.out.println("From:" + remote + ">"+message);
                        if(message == null || message.isEmpty()) {
                            message = null;
                        }
                        else {
                            message = messagenum++ + " Output> " + message;
                        }*/
                        //check if data was inserted
                        if(pass == null) {
                            message.setCommand("Success");
                            message.setCabbage(cabDao.getCabbageByUUID(uuid));
                            System.out.println("Cabbage has been added");
                        }else{
                            message.setCommand("fail");
                        }
                    }
                    //send message back to client
                        output.writeObject(message);
                        output.flush();
                    } while (!message.getCommand().equalsIgnoreCase("disconnect"));
                    System.out.println(remote + " disconnected via request");
                } catch (IOException exception) {
                    System.err.println(exception.getMessage());
                    exception.printStackTrace();
                    message.setCommand("fail");
                    try {
                        output.writeObject(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }catch (ClassNotFoundException exception) {
                    System.out.println(exception.getMessage());
                    exception.printStackTrace();
                    message.setCommand("fail");
                    try {
                        output.writeObject(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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

    /**
     * Starts serer and creates a new thread for each client
     */
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
                //gets connection and starts new thread for client
                connection = server.accept();
                talkToClient(connection);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }
}
