package DataStructures;

import java.io.Serializable;
import java.util.ArrayList;

public class Conversation implements Serializable {

    private int id;
    private ArrayList<String> users; // usernames
    private ArrayList<Message> messages;

    //region Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<String> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<String> users) {
        this.users = users;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }
    //endregion

    //region Methods

    public void addMessage(Message m){
        messages.add(m);
    }

    public void addUser(String username){
        if(!users.contains(username)){
            users.add(username);
        }
    }

    public void removeUser(String username){
        if(users.contains(username)){
            users.remove(username);
        }
    }
    //endregion


    public Conversation(int id) {
        this.id = id;
        users = new ArrayList<>();
        messages = new ArrayList<>();
    }
}
