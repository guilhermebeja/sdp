package Client;

import DataStructures.Conversation;
import DataStructures.Message;
import Interfaces.Observer;

import java.util.ArrayList;
import java.util.HashMap;

public class Client {
    ArrayList<Observer> observers;
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
        observers  =new ArrayList<>();
        socket = new ClientSocket(port, this);
        socket.start();
    }

    public void addObserver(Observer obs){
        observers.add(obs);
    }

    public void onMessageReceive(Message message){
        if(!conversations.containsKey(genKey(message.getSenderIP(), message.getSenderPort()))){
            conversations.put(genKey(message.getSenderIP(), message.getSenderPort()), new Conversation());
        }

        Conversation c = conversations.get(genKey(message.getSenderIP(), message.getSenderPort()));
        c.addMessage(message);
        System.out.println(message.getSenderIP() +": " + message.getMessage());
        notifyObservers(c);
    }

    public void sendMessage(String destIP, int destPort, String msg){
        Message message = new Message(ip,destIP,port, destPort, msg);

        if(!conversations.containsKey(genKey(destIP, destPort))){
            conversations.put(genKey(destIP, destPort), new Conversation());
        }

        Conversation c = conversations.get(genKey(destIP, destPort));
        c.addMessage(message);

        socket.sendDP(message);

        notifyObservers(c);
    }

    private void notifyObservers(Conversation c){
        for(Observer obs : observers){
            obs.update(c);
        }
    }
    private String genKey(String ip, int port){
        return ip + ":" + Integer.toString(port);
    }

}

