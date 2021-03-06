package Client;


import DataStructures.Conversation;
import DataStructures.Message;
import Exceptions.ClientException;
import Exceptions.ServerConnectionTimeoutException;
import Extras.Pair;
import Interfaces.Observer;
import Server.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.*;
import java.util.function.Function;

public class Client extends Thread{
    private static final String SERVER_ADDRESS = "localhost";
    private static final int PORT = 7777;
    private Socket server;
    private ObjectOutputStream oos = null;
    private ObjectInputStream ois = null;
    private String username;
    private HashMap<Integer, Function<ServerResponse, Object>> waitingList = new HashMap<>();
    private int currentID = 0;
    private ArrayList<Conversation> conversations = new ArrayList<>();
    private ArrayList<Friend> friends = new ArrayList<>();

    public ArrayList<Observer> observers = new ArrayList<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<Friend> getFriends() {
        return friends;
    }

    public Client( ) throws ClientException{
        server = new Socket();
        try {
            server.connect(new InetSocketAddress(SERVER_ADDRESS, PORT), 2000);
            oos = new ObjectOutputStream(server.getOutputStream());
            ois = new ObjectInputStream(server.getInputStream());
            start();

        } catch (IOException e) {
            throw new ServerConnectionTimeoutException("Couldn't connect to the server on \'" + SERVER_ADDRESS + ":" + PORT+"\'");
        }
    }

    public void serverRequest(String line, Function<ServerResponse, Object> callable){
        try {
            int id = getNewID();
            waitingList.put(id, callable);
            String toSend = line.trim();
            if(toSend.contains("?")){
                toSend+="&reqID="+Integer.toString(id);
            }
            else{
                toSend+="?reqID="+Integer.toString(id);
            }
            oos.writeObject(Utilities.encrypt(toSend));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void serverRequest(String line){
        try {
            oos.writeObject(Utilities.encrypt(line));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(int cID, String message){
        message=message.replace(" ", "+");
        serverRequest("POST /conversation/"+cID+"/messages/add?sender="+username+"&content="+message+"&time="+System.currentTimeMillis());
    }

    public void getConversation(int conversationID){
        serverRequest("GET /conversation/"+conversationID, rsp -> {
            Conversation conv = (Conversation)rsp.getResponse();
            observers.forEach(o -> o.changeCurrentConversation(conv));
            return null;
        });
    }
    private int getNewID(){
        return currentID++;
    }

    public void createFriendRequest(String username){
        if(friends.stream().noneMatch(f -> f.username.equals(username))){
            serverRequest("POST /user/"+this.username+"/friends/request?friend="+username, rsp -> {
                if(rsp.getStatusCode().equals(StatusCode.OK)){
                    Friend newFriend = new Friend(username, true, false);
                    this.friends.add(newFriend);
                    observers.forEach(o -> o.newSentFriendRequest(newFriend));
                }
                return null;
            });
        }

    }

    public void acceptFriendRequest(String username){
        Optional<Friend> friend = friends.stream().filter(f -> f.username.equals(username)).findFirst();

        if(friend.isPresent() && friend.get().isFriendRequestReceived){
            serverRequest("POST /user/"+this.username+"/friends/accept?friend="+username, rsp -> {
                if(rsp.getStatusCode().equals(StatusCode.OK)){
                    Friend nf = new Friend(username, false, false);
                    this.friends.add(nf);
                    observers.forEach(o -> o.friendRequestAccepted(nf));
                }
                return null;
            });
        }
    }

    public void friendRequestAccepted(String username){
        Optional<Friend> friend = friends.stream().filter(f -> f.username.equals(username)).findFirst();

        if(friend.isPresent() && friend.get().friendRequestSent){
            friend.get().friendRequestSent = false;
            friend.get().isFriendRequestReceived = false;
            observers.forEach(o -> o.friendRequestAccepted(friend.get()));
        }
    }

    public void removeFriend(String username){
        Optional<Friend> friend = friends.stream().filter(f -> f.username.equals(username)).findFirst();

        if(friend.isPresent()){
            serverRequest("POST /user/"+this.username+"/friends/remove?friend="+username, rsp -> {
                if(rsp.getStatusCode().equals(StatusCode.OK)){
                    this.friends.removeIf(f -> f.username.equals(username));
                    observers.forEach(o->o.removedFriend(friend.get()));
                }
                return null;
            });
        }
    }

    public void friendRemoved(String username){
        Optional<Friend> friend = friends.stream().filter(f -> f.username.equals(username)).findFirst();
        if(friend.isPresent()){
            this.friends.removeIf(f -> f.username.equals(username));
            observers.forEach(o->o.removedFriend(friend.get()));
        }
    }

    public void newIncomingFriendRequest(String username){
        if(friends.stream().noneMatch(f -> f.username.equals(username))){
            Friend nf = new Friend(username, false, true);
            friends.add(nf);
            observers.forEach(o->o.newReceivedFriendRequest(nf));
        }
    }

    public void populateFriendList(){
        serverRequest("GET /user/"+this.username+"/friends", rsp -> {
            if(rsp.getStatusCode().equals(StatusCode.OK)){
                HashMap<String, ArrayList<String>> friendList = (HashMap<String, ArrayList<String>>)rsp.getResponse();

                for(String f : friendList.get("friends")){
                    friends.add(new Friend(f, false, false));
                }

                for(String f : friendList.get("requestSent")){
                    friends.add(new Friend(f, true, false));
                }

                for(String f : friendList.get("requestReceived")){
                    friends.add(new Friend(f, false, true));
                }

                observers.forEach(o->o.updateFriendList(null));
            }
            return null;
        });
    }

    public void incomingMessage(int convID, Message m){
        observers.forEach(observer -> observer.newMessage(convID, m));
    }

    public void getUserIP(String username){
        serverRequest("GET /user/"+username+"/ip", rsp -> {
            observers.forEach(o -> o.openSecretConversation((String)rsp.getResponse()));
            return null;
        });
    }

    private void processNotification(Notification notification){
        if(notification.type.equals(Notification.NotificationType.NEW_FRIEND_REQUEST)){
            newIncomingFriendRequest((String)notification.data);
        }
        else if(notification.type.equals(Notification.NotificationType.FRIEND_REQUEST_ACCEPTED)){
            friendRequestAccepted((String)notification.data);
        }
        else if(notification.type.equals(Notification.NotificationType.FRIEND_REMOVED)){
            friendRemoved((String)notification.data);
        }

        else if(notification.type.equals(Notification.NotificationType.NEW_MESSAGE)){
            Pair<Integer, Message> pair = (Pair<Integer, Message>)notification.data;
            incomingMessage(pair.getLeft(), pair.getRight());
        }
    }

    @Override
    public void run() {
        try {
            while(true){
                if(server.getInputStream().available()!=0){
                    ServerResponse sr = (ServerResponse)Utilities.decrypt((byte[])ois.readObject());
                    if(sr.getReqID() != -1){
                        if(waitingList.containsKey(sr.getReqID())){
                            waitingList.get(sr.getReqID()).apply(sr); // apply function and send server response.
                            waitingList.remove(sr.getReqID()); // Remove waiting request function
                        }
                    }
                    else{
                        if(sr.getStatusCode().equals(StatusCode.NOTIFICATION)){
                            processNotification((Notification)sr.getResponse());
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public class Friend {
        public String username;
        public boolean friendRequestSent;
        public boolean isFriendRequestReceived;

        public Friend(String username, boolean friendRequestSent, boolean isFriendRequestReceived) {
            this.username = username;
            this.friendRequestSent = friendRequestSent;
            this.isFriendRequestReceived = isFriendRequestReceived;
        }

        @Override
        public String toString() {
            return username + (friendRequestSent? " (sent)" : isFriendRequestReceived? " (received)": "");
        }
    }
}
