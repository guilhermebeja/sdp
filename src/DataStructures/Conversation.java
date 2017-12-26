package DataStructures;

import java.io.Serializable;
import java.util.ArrayList;

public class Conversation implements Sendable, Serializable {

    private ArrayList<Message> messages;

    //region Getters and Setters
    @Override
    public String getSenderIP() {
        return null;
    }

    @Override
    public int getSenderPort() {
        return 0;
    }

    @Override
    public String getReceiverIP() {
        return null;
    }

    @Override
    public int getReceiverPort() {
        return 0;
    }

    @Override
    public String getMessage() {
        return null;
    }
    //endregion

    //region Constructors
    public Conversation() {
        messages = new ArrayList<>();
    }

    public Conversation(ArrayList<Message> messages) {
        this.messages = messages;
    }
    //endregion

    //region Methods
    public void addMessage(Message msg){
        messages.add(msg);
    }

    @Override
    public int length() {
        return this.length();
    }
    //endregion
}
