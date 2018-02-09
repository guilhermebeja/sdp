package Server;

import Client.Utilities;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientSocket extends Thread{

    ObjectInputStream ois = null;
    ObjectOutputStream oos = null;
    private Socket soc;
    private Server server;
    private long lastTime = 0;

    public boolean isLoggedIn = false;
    public String username;

    public ClientSocket(Socket soc, Server server){
        this.soc = soc;
        this.server = server;

        try {
            ois = new ObjectInputStream(soc.getInputStream());
            oos = new ObjectOutputStream(soc.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(ServerResponse toSend){
        try {
            oos.writeObject(Utilities.encrypt(toSend));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printRequest(String line){
        System.out.println(soc.getInetAddress().toString()+ ": " + line);
    }

    @Override
    public void run() {
        try {
            while(true){
                if(soc.getInputStream().available()!=0){
                    String line = (String) Utilities.decrypt((byte[])ois.readObject());
                    printRequest(line);
                    ServerRequest req = new ServerRequest(line, soc.getInetAddress().getHostAddress(), soc.getPort());
                    server.respond(req, this);

                    // Disconnect Request, end thread
                    if(req.getRequestType().equals(RequestType.DISCONNECT)){
                        break;
                    }
                }
            }

            ois.close();
            oos.close();
            soc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
