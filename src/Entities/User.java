package Entities;

import DataStructures.Conversation;

import java.util.ArrayList;

public class User {
    private String nickname, password, ip;
    private int port;

    private ArrayList<Conversation> conversations;
    private ArrayList<User> friends;

    //region Getters and Setter
    public ArrayList<User> getFriends() {
        return friends;
    }
    public void setFriends(ArrayList<User> friends) {
        this.friends = friends;
    }
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
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
    public User(String nickname, String password, String ip, int port) {
        this.nickname = nickname;
        this.password = password;
        this.ip = ip;
        this.port = port;
        conversations = new ArrayList<>();
        friends = new ArrayList<>();
    }
    //endregion

    //region Methods
    public void addFriend(User f){
        friends.add(f);
    }
    //endregion
}
