package Client;

import Server.ServerResponse;
import Server.StatusCode;
import sun.security.util.ByteArrayLexOrder;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

    public Client(String username) {
        this.username = username;

    }

    public void login(){

    }

    public void logout(){

    }

    public void sendMessage(){

    }

    public void startConversation(String conversationID){

    }

    public ArrayList<String> getAllFriends() throws IOException, ClassNotFoundException {
        return (ArrayList<String>) serverRequest("GET /user/"+username+"/friends").getResponse();
    }

    public StatusCode addFriend(String friend){
        try {
            return serverRequest("POST /user/"+username+"/friends/add friend="+friend).getStatusCode();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return StatusCode.ERROR;
    }

    public void removeFriend(String friend){
        try {
            serverRequest("POST /user/"+username+"/friends/remove friend="+friend);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ServerResponse serverRequest(String line) throws IOException, ClassNotFoundException {
        server = new Socket(SERVER_ADDRESS, PORT);
        oos = new ObjectOutputStream(server.getOutputStream());
        ois = new ObjectInputStream(server.getInputStream());

        oos.writeObject(line);

        ServerResponse sr = (ServerResponse) ois.readObject();
        System.out.println(sr.getResponse());

        oos.close();
        ois.close();
        return sr;
    }


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Client client = new Client("Ciscomarte");
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        while(true){
            if(line.equals("EXIT")){
                break;
            }

            client.serverRequest(line);

            line = scanner.nextLine();
        }
    }
}
