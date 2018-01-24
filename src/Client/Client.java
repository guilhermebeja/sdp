package Client;

import DataStructures.Conversation;
import DataStructures.Message;
import Server.ServerResponse;
import Server.StatusCode;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {

    private static String SERVER_ADDRESS = "192.168.1.70";
    private static int PORT = 8081;
    private Socket server;
    private ObjectOutputStream oos = null;
    private ObjectInputStream ois = null;

    private String username;

    public String getUsername() {
        return username;
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

    public void login(){

    }

    public void logout(){

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
        if(((ArrayList)res.getResponse()).size()!=0){
            c.setMessages((ArrayList<Message>) res.getResponse());
        }


        return c;
    }

    public ArrayList<String> getAllFriends() {
        ServerResponse res = new ServerResponse(StatusCode.ERROR, "");

        serverRequest("GET /user/"+username+"/friends", res);

        if(res.getStatusCode().equals(StatusCode.OK)){
            return (ArrayList<String>)res.getResponse();
        }

        return new ArrayList<String>();
    }

    public StatusCode addFriend(String friend){
        ServerResponse res = new ServerResponse(StatusCode.ERROR, "");

        serverRequest("POST /user/"+username+"/friends/add friend="+friend, res);
        return res.getStatusCode();

    }

    public void removeFriend(String friend){
        ServerResponse res = new ServerResponse(StatusCode.ERROR, "");

        serverRequest("POST /user/"+username+"/friends/remove friend="+friend, res);

    }

    public void serverRequest(String line, ServerResponse resp){
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

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Client client = new Client("Ciscomarte");
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
