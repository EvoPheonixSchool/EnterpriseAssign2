package datatransfer;

import java.io.Serializable;

// imports as needed

/**
 * creates a serialized object to send cabbages
 * Class provided by Stan Pieda
 * Modified by Sheldon McGrath
 */
public class Message implements Serializable{
/* 
 * code here please 
 */
    private static final long serialVersionUID = 1L;
    private String command;
    private Cabbage cabbage;

    /**
     * Constructor to set command
     * @param command command to be sent between client and server
     */
    public Message(String command){
        this.command = command;
    }

    /**
     * constructor to set all values
     * @param command command to be sent between client and server
     * @param cabbage
     */
    public Message(String command, Cabbage cabbage){
        this.command = command;
        this.cabbage = cabbage;
    }

    public String getCommand(){
        return command;
    }

    public void setCommand(String command){
        this.command = command;
    }

    public Cabbage getCabbage(){
        return cabbage;
    }

    public void setCabbage(Cabbage cabbage){
        this.cabbage = cabbage;
    }
}
