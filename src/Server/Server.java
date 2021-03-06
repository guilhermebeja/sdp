package Server;

import DataStructures.Conversation;
import Entities.User;
import Server.Contexts.*;
import java.io.*;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Server extends Thread{

    private Mapper mapper;
    private ServerSocket serverSoc;
    private boolean listen = false;

    private ArrayList<ClientSocket> sockets = new ArrayList<>();

    public HashMap<String, ClientSocket> usersLogged = new HashMap<>();

    //region Constructor
    public Server(int port) {
        try {
            File db = new File("./files/db.dat");
            if(db.exists()){
                loadDB();

            }
            else{
                db.getParentFile().mkdirs();
                writeDB();
            }

            this.serverSoc = new ServerSocket(port);
            this.listen = true;
            mapper = new Mapper();
            configMapper();

            System.out.println("Server running on port: " + serverSoc.getLocalPort());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void setListen(boolean listen){
        this.listen = listen;
    }

    public void closeServerSocket(){
        try {
            serverSoc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeDB(){
        FileOutputStream fout = null;
        ObjectOutputStream oos = null;

        try {

            fout = new FileOutputStream("./files/db.dat");
            oos = new ObjectOutputStream(fout);

            ArrayList<Object> structure = new ArrayList<>();
            structure.add(Database.getUsers());
            structure.add(Database.getConversations());

            oos.writeObject(structure);

            System.out.println("Done");

        } catch (Exception ex) {

            ex.printStackTrace();

        } finally {

            if (fout != null) {
                try {
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static void loadDB(){
        FileInputStream fis = null;
        ObjectInputStream ois = null;

        try {
            fis = new FileInputStream("./files/db.dat");
            ois = new ObjectInputStream(fis);

            ArrayList<Object> structure = (ArrayList<Object>)ois.readObject();

            Database.setUsers((ArrayList<User>)structure.get(0));
            Database.setConversations((ArrayList<Conversation>)structure.get(1));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    //endregion

    //region Methods

    public void run(){
        try{
            while(listen){
                ClientSocket cs = new ClientSocket(serverSoc.accept(), this);
                sockets.add(cs);
                cs.start();
            }
            serverSoc.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Sends a notification to a client if that client is connected
     */
    public void sendNotification(String username, ServerResponse toSend){
        if(usersLogged.containsKey(username)){
            usersLogged.get(username).notificationStack.add(toSend);
        }
    }

    private void configMapper(){
        mapper.registerRoute(RequestType.GET, "/user/:username/ip", new GetUserIP(this), true); // returns user IP. //GET /user/ciscomarte/ip
        mapper.registerRoute(RequestType.GET, "/user/:username/conversations", new GetUserConversations(this), true); // returns user IP. //GET /user/ciscomarte/ip
        mapper.registerRoute(RequestType.GET, "/user/:username/notifications", new GetUserNotifications(this), true);
        mapper.registerRoute(RequestType.GET, "/user/:username/friends", new GetUserFriends(this), true); // returns user friend list (list of usernames// )
        mapper.registerRoute(RequestType.POST,"/user/:username/friends/request", new PostUserRequestFriend(this), true);
        mapper.registerRoute(RequestType.POST,"/user/:username/friends/accept", new PostUserFriendsAccept(this), true);
        mapper.registerRoute(RequestType.POST,"/user/:username/friends/remove", new PostUserFriendsRemove(this), true);
        mapper.registerRoute(RequestType.POST,"/user/create", new PostUserCreate(this), false);
        mapper.registerRoute(RequestType.POST,"/user/:username/update", new PostUserUpdate(this), true);

        mapper.registerRoute(RequestType.GET, "/conversation/:id", new GetConversationByID(this), true);
        mapper.registerRoute(RequestType.GET, "/conversation/:id/users", new GetConversationUsers(this), true);
        mapper.registerRoute(RequestType.GET, "/conversation/:id/messages", new GetConversationMessages(this), true);
        mapper.registerRoute(RequestType.POST,"/conversation/:id/create", new PostConversationCreate(this), true);
        mapper.registerRoute(RequestType.POST,"/conversation/:id/messages/add", new PostConversationAddMessage(this), true);
        mapper.registerRoute(RequestType.POST,"/conversation/:id/users/add", new PostConversationAddUser(this), true);
        mapper.registerRoute(RequestType.POST,"/conversation/:id/users/remove", new PostConversationRemoveUser(this), true);

        mapper.registerRoute(RequestType.POST, "/login", new Login(this), false);
        mapper.registerRoute(RequestType.POST, "/logout", new Logout(this), true);
    }

    public void respond(ServerRequest req, ClientSocket client){
        ServerResponse rs;
        ResponseContext context = mapper.getContext(req);

        if(context != null){
            rs = context.getResponse(req.getParameters(), client);
        }
        else{
            rs = new ServerResponse(StatusCode.FORBBIDEN, "Not Authorized");
        }

        if(req.getParameters().containsParameter("reqID")){
            rs.setReqID(Integer.parseInt(req.getParameters().getParameter("reqID").get(0)));
        }

        client.sendMessage(rs);
    }

    public static void main(String[] args) {
        Server server = new Server(7777);
        server.start();
        Scanner scanner = new Scanner(System.in);

        String line = scanner.nextLine();
        while(true){
            if(line.equals("EXIT")){
                server.closeServerSocket();
                break;
            }
        }

        try {
            server.join();
            writeDB();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //endregion
}
