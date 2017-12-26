package Client;

import DataStructures.Conversation;
import DataStructures.Message;

import java.util.HashMap;

public class Client {
    ClientSocket socket;

    private HashMap<String, Conversation> conversations;

    private String nickname, password, ip;
    private int port;

    public Client(String nickname, String password,String ip, int port) {
        this.nickname = nickname;
        this.password = password;
        this.ip = ip;
        this.port = port;
        conversations = new HashMap<>();

        socket = new ClientSocket(port, this);
        socket.start();
    }

    public void onMessageReceive(Message message){
        if(!conversations.containsKey(message.getSenderIP())){
            conversations.put(genKey(message.getSenderIP(), message.getSenderPort()), new Conversation());
        }

        Conversation c = conversations.get(genKey(message.getSenderIP(), message.getSenderPort()));
        c.addMessage(message);
        System.out.println(message.getSenderIP() +": " + message.getMessage());
    }

    public void sendMessage(String destIP, int destPort, String msg){
        Message message = new Message(ip,destIP,port, destPort, msg);

        if(!conversations.containsKey(destIP)){
            conversations.put(genKey(destIP, destPort), new Conversation());
        }

        Conversation c = conversations.get(genKey(destIP, destPort));
        c.addMessage(message);

        socket.sendDP(message);
    }

    private String genKey(String ip, int port){
        return ip + ":" + Integer.toString(port);
    }

}

