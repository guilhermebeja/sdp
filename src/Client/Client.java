package Client;

import DataStructures.ClientNotification;
import DataStructures.Conversation;
import DataStructures.Message;
import Server.ClientUpdateWorker;
import Server.ServerResponse;
import Server.StatusCode;
import UI.Contact;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client extends Thread{

    private static String SERVER_ADDRESS = "192.168.1.70";
    private static int PORT = 8081;
    private Socket server;
    private ObjectOutputStream oos = null;
    private ObjectInputStream ois = null;

    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

    private ArrayList<Contact> contacts = new ArrayList<>();

    private String username;
    private ClientUpdateWorker worker;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Client(String username) {
        this.username = username;
        server = new Socket();
        try {
            server.connect(new InetSocketAddress(SERVER_ADDRESS, PORT), 2000);

            oos = new ObjectOutputStream(server.getOutputStream());
            ois = new ObjectInputStream(server.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean login(String username, String password){
        ServerResponse res = new ServerResponse(StatusCode.ERROR, "");
        serverRequest("POST /login username="+username+"&password="+password, res);
        if(res.getStatusCode().equals(StatusCode.OK)){
            return true;
        }
        return false;
    }

    public boolean logout(){
        ServerResponse res = new ServerResponse(StatusCode.ERROR, "");
        serverRequest("POST /logout username="+username, res);
        if(res.getStatusCode().equals(StatusCode.OK)){
            return true;
        }
        return false;
    }

    public String register(String username, String password, String email){
        ServerResponse res = new ServerResponse(StatusCode.ERROR, "");
        serverRequest("POST /user/create username="+username+"&password="+password+"&email="+email, res);
        if(res.getStatusCode().equals(StatusCode.OK)){
            return "";
        }
        else{
            return (String)res.getResponse();
        }

    }

    public void sendMessage(int conversationID, String content){
        ServerResponse res = new ServerResponse(StatusCode.ERROR, "");
        serverRequest("POST /conversation/"+conversationID+"/messages/add sender="+username+"&content="+content.replace(" ", "+")+"&time="+System.currentTimeMillis(), res);

    }

    public Conversation startConversation(int conversationID){
        ServerResponse res = new ServerResponse(StatusCode.ERROR, "");

        serverRequest("GET /conversation/"+conversationID+"/messages", res);

        if(res.getStatusCode().equals(StatusCode.NOT_FOUND)){
            serverRequest("POST /conversation/"+conversationID+"/create username="+getUsername(), res);
        }

        Conversation c = new Conversation(conversationID);
        if(res.getResponse() instanceof ArrayList){
            if(((ArrayList)res.getResponse()).size()!=0){
                c.setMessages((ArrayList<Message>) res.getResponse());
            }
        }

        return c;
    }

    public ArrayList<String> updateContactList() {
        ServerResponse res = new ServerResponse(StatusCode.ERROR, "");

        serverRequest("GET /user/"+username+"/friends", res);

        if(res.getStatusCode().equals(StatusCode.OK)){
            return (ArrayList<String>)res.getResponse();
        }

        return new ArrayList<String>();
    }

    public boolean addFriend(String friend){
        ServerResponse res = new ServerResponse(StatusCode.ERROR, "");

        serverRequest("POST /user/"+username+"/friends/add friend="+friend, res);
        if(res.getStatusCode().equals(StatusCode.OK)){
            return true;
        }
        return false;
    }

    public ArrayList<String> getAllFriends(){
        ServerResponse res = new ServerResponse(StatusCode.ERROR, "");
        serverRequest("GET /user/"+username+"/friends", res);

        if(((ArrayList)res.getResponse()).size() > 0){
            return (ArrayList<String>)(res.getResponse());
        }
        return new ArrayList<>();
    }

    public void removeFriend(String friend){
        ServerResponse res = new ServerResponse(StatusCode.ERROR, "");
        serverRequest("POST /user/"+username+"/friends/remove friend="+friend, res);

    }

    public void addContact(){

    }

    public synchronized void serverRequest(String line, ServerResponse resp){
        ServerResponse sr = new ServerResponse(StatusCode.ERROR, "Internal Error");

        try {
            oos.writeObject(line);
            Object o = ois.readObject();
            sr = (ServerResponse) o;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            resp.setStatusCode(sr.getStatusCode());
            resp.setResponse(sr.getResponse());

        }
    }

    @Override
    public void run() {
        ServerResponse sr;

        while(true){
            sr = new ServerResponse(StatusCode.ERROR, "Internal Error");
            serverRequest("GET /users/"+username+"/notifications", sr);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Client client = new Client("Ciscomarte");
        client.start();


        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        while(true){
            if(line.equals("EXIT")){
                break;
            }

            ServerResponse res = new ServerResponse(StatusCode.ERROR, "");

            client.serverRequest(line, res);

            System.out.println(res);
            line = scanner.nextLine();
        }

    }


}
