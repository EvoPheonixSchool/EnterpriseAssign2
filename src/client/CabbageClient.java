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

// imports as needed
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
    private Message capsle;

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
    public CabbageClient(String serverName, int portNum){
        this.serverName = serverName;
        this.portNum = portNum;
    }
    public void runClient(){
        String myHostName = null;
        try {
            InetAddress myHost = Inet4Address.getLocalHost();
            myHostName = myHost.getHostName();
        } catch (UnknownHostException e1) {
            e1.printStackTrace();
        }
        try {
            connection = new Socket(InetAddress.getByName(serverName), portNum);
            output = new ObjectOutputStream (connection.getOutputStream());
            input = new ObjectInputStream( connection.getInputStream());
            do {
                cabbage = new Cabbage();
                System.out.println("Sheldon McGrath " + dateFormat.format(date));
                System.out.print("Create a new cabbage.\nEnter Alpha: \n");
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

                capsle = new Message("Insert",cabbage);
                output.writeObject(capsle);
                output.flush();
                capsle = (Message) input.readObject();

                cabbage = capsle.getCabbage();
                switch(capsle.getCommand()){
                    case "success":
                        System.out.println("Cabbage has successfully been entered.");
                        System.out.println("alpha: " + cabbage);
                        break;
                    case "failure":

                        break;
                    case "disconnect":

                        break;
                }

            } while (capsle.getCommand() == "success");
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
