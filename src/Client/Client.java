package Client;

import DataStructures.Conversation;
import DataStructures.Message;
import Interfaces.Observer;
import Server.*;
import Server.ServerResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Client {
    ArrayList<Observer> observers;
    public ClientSocket socket;
    private HashMap<String, Conversation> conversations;
    private String nickname, password, ip;
    private int port;


    //region Getters and Setters
    public String getIP(){
        return ip;
    }
    public int getPort(){
        return port;
    }
    //endregion

    //region Constructors
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
    //endregion

    //region Methods
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

    public ServerResponse requestServer(ServerRequest sr) throws IOException, ClassNotFoundException {
        Socket s  = new Socket("127.0.0.1", 8081);

        ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
        ObjectInputStream is = new ObjectInputStream(s.getInputStream());

        oos.writeObject(sr);

        ServerResponse resp = (ServerResponse) is.readObject();
        oos.close();
        is.close();
        s.close();

        return resp;
    }

    private void notifyObservers(Conversation c){
        for(Observer obs : observers){
            obs.update(c);
        }
    }

    private String genKey(String ip, int port){
        return ip + ":" + Integer.toString(port);
    }
    //endregion
}

