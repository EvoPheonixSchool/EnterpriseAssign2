package client;

import datatransfer.Cabbage;
import datatransfer.Message;

import java.io.*;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

// imports as needed

/**
 * Class provided by Stan Pieda
 * modified by Sheldon McGrath
 *
 * This class is a client application for sending cabbages to a server
 */
public class CabbageClient {
/* 
 * code here please 
 */

    private Socket connection;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private String alpha,beta,charlie,delta;
    private int lineno;
    private String serverName = "localhost";
    private int portNum = 8081;
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Date date = new Date();

    private Cabbage cabbage;
    private Message capsle = new Message(null);

    /**
     * Main method, takes arguments for an ip and port number
     * @param args
     */
    public static void main(String[] args) {
        switch (args.length){
            case 2:
                (new CabbageClient(args[1],Integer.parseInt(args[2]))).runClient();
                break;
            case 1:
                (new CabbageClient("localhost",Integer.parseInt(args[1]))).runClient();
                break;
            default:
                (new CabbageClient("localhost",8081)).runClient();
        }

    }

    /**
     * Constructor takes an ip and port number
     * @param serverName ip of server
     * @param portNum port number
     */
    public CabbageClient(String serverName, int portNum){
        this.serverName = serverName;
        this.portNum = portNum;
    }

    /**
     * main logic for client
     *
     * Creates connection to server
     * gets input for cabbage
     * creates cabbage object
     * sends cabbage in message object
     */
    public void runClient(){
        //String myHostName = null;
        try {
            InetAddress myHost = Inet4Address.getLocalHost();
            //myHostName = myHost.getHostName();
        } catch (UnknownHostException e1) {
            e1.printStackTrace();
        }
        try {
            //creates connteion to server from given ip and port
            connection = new Socket(InetAddress.getByName(serverName), portNum);
            output = new ObjectOutputStream (connection.getOutputStream());
            input = new ObjectInputStream( connection.getInputStream());
            System.out.println("Sheldon McGrath " + dateFormat.format(date));
            do {

                //ask to continue
                System.out.print("Create a new cabbage(y/n):");
                if(br.readLine().equalsIgnoreCase("n")){
                    capsle.setCommand("disconnect");
                }else {
                    //get info for new cabbage
                    cabbage = new Cabbage();
                    System.out.println("Alpha: ");
                    alpha = br.readLine();
                    cabbage.setAlpha(alpha);
                    System.out.println("Enter Beta: ");
                    beta = br.readLine();
                    cabbage.setBeta(beta);
                    System.out.println("Enter Charlie: ");
                    charlie = br.readLine();
                    cabbage.setCharlie(charlie);
                    System.out.println("Enter Delta: ");
                    delta = br.readLine();
                    cabbage.setDelta(delta);
                    System.out.println("Enter line number: ");
                    lineno = Integer.parseInt(br.readLine());
                    cabbage.setLineNumber(lineno);
                    System.out.println(lineno);
                    UUID uuid = UUID.randomUUID();
                    cabbage.setUUID(uuid.toString());

                    //create message object
                    capsle.setCommand("insert");
                    capsle.setCabbage(cabbage);

                }
                //send cabbage to server
                output.writeObject(capsle);
                output.flush();

                //get message back from server
                capsle = (Message) input.readObject();
                //System.out.println(capsle.getCommand());

                //read message from server
                switch(capsle.getCommand()){
                    case "Success":
                        cabbage = capsle.getCabbage();
                        System.out.println("Cabbage has successfully been entered.");
                        System.out.println("Cabbage: " + cabbage + "\n");
                        break;
                    case "fail":
                        System.out.println("An error has occurred");
                        break;
                    case "disconnect":
                        System.out.println("Disconnecting from server");
                        break;
                }

            } while (! capsle.getCommand().equalsIgnoreCase("disconnect"));
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
            exception.printStackTrace();
        }catch (ClassNotFoundException exception) {
            System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
        finally{
            try{if(input != null){input.close();}}catch(IOException ex){
                System.out.println(ex.getMessage());}
            try{if(output != null){output.flush(); output.close();}}catch(IOException ex){
                System.out.println(ex.getMessage());}
            try{if(connection != null){connection.close();}}catch(IOException ex){
                System.out.println(ex.getMessage());}
        }
    }
}
