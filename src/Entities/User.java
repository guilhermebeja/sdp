package Entities;

import DataStructures.Conversation;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Identifies a user
 */
public class User implements Serializable{
    private String username, password, ip;
    private int port;

    private ArrayList<String> friends; // list of usernames
    private ArrayList<String> pendingFriends;
    private ArrayList<String> pendingAccept;
    private boolean isLoggedIn;

    //region Getters and Setter
    public ArrayList<String> getFriends() {
        return friends;
    }
    public void setFriends(ArrayList<String> friends) {
        this.friends = friends;
    }

    public ArrayList<String> getPendingFriends() {
        return pendingFriends;
    }

    public void setPendingFriends(ArrayList<String> pendingFriends) {
        this.pendingFriends = pendingFriends;
    }

    public ArrayList<String> getPendingAccept() {
        return pendingAccept;
    }

    public void setPendingAccept(ArrayList<String> pendingAccept) {
        this.pendingAccept = pendingAccept;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
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
        pendingAccept = new ArrayList<>();
        this.username = username;
        this.password = password;
        this.ip = ip;
        this.port = port;
        friends = new ArrayList<>();
        pendingFriends = new ArrayList<>();
        isLoggedIn = false;
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

    public void addPendingFriend(String friend){
        pendingFriends.add(friend);
    }

    public void removePendingFriend(String friend){
        pendingFriends.remove(friend);
    }

    public String getNetAddress(){
        return ip+ ":"+ port;
    }

    //endregion
}
