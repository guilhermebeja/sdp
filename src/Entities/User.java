package Entities;

import DataStructures.Conversation;

import java.util.ArrayList;

/**
 * Identifies a user
 */
public class User {
    private String username, password, ip;
    private int port;

    private ArrayList<String> friends; // list of usernames

    //region Getters and Setter
    public ArrayList<String> getFriends() {
        return friends;
    }
    public void setFriends(ArrayList<String> friends) {
        this.friends = friends;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public int getPort() {
        return port;
    }
    public void setPort(int port) {
        this.port = port;
    }
    //endregion

    //region Constructors
    public User(String username, String password, String ip, int port) {
        this.username = username;
        this.password = password;
        this.ip = ip;
        this.port = port;
        friends = new ArrayList<>();
    }
    //endregion

    //region Methods
    public void addFriend(String friend){
        if(!friends.contains(friend)){
            friends.add(friend);
        }
    }

    public void removeFriend(String friend){
        friends.remove(friend);
    }

    public String getNetAddress(){
        return ip+ ":"+ port;
    }

    //endregion
}
