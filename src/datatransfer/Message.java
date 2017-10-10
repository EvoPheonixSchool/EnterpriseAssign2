package datatransfer;

import java.io.Serializable;

// imports as needed
public class Message implements Serializable{
/* 
 * code here please 
 */
    private static final long serialVersionUID = 1L;
    private String command;
    private Cabbage cabbage;

    public Message(String command){
        this.command = command;
    }

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
