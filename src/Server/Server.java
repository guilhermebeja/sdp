package Server;

import Server.Contexts.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread{
    private Mapper mapper;
    private ServerSocket serverSoc;
    private boolean listen = false;

    public Server(int port) {
        try {
            this.serverSoc = new ServerSocket(port);
            this.listen = true;
            mapper = new Mapper();
            configMapper();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void run(){
        try{
            while(listen){
                Socket soc = serverSoc.accept();

                reply(soc);
                soc.close();
            }
            serverSoc.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    private void configMapper(){
        mapper.registerRoute(RequestType.GET, "user", new GetUser());
        mapper.registerRoute(RequestType.GET, "conversation", new GetConversation());
        mapper.registerRoute(RequestType.GET, "friends", new GetFriendList());
        mapper.registerRoute(RequestType.GET, "login", new Login());
        mapper.registerRoute(RequestType.POST, "user", new PostUser());

    }

    public void reply(Socket soc){
        try{

            ObjectInputStream oos = new ObjectInputStream(soc.getInputStream());
            ObjectOutputStream os = new ObjectOutputStream(soc.getOutputStream());

            ServerRequest serverRequest = (ServerRequest)oos.readObject();
            ResponseContext context = mapper.getContext(serverRequest);
            ServerResponse rs;
            if(context != null){
                rs = context.getResponse(serverRequest.getHeaders());
            }
            else{
                rs = new ServerResponse(StatusCode.ERROR, "Request not valid!");
            }

            os.writeObject(rs);
            os.close();
            oos.close();


        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        Server server = new Server(8081);
    }

}
