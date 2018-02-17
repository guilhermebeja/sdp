package Server;

import Client.Utilities;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Queue;

public class ClientSocket extends Thread{

    ObjectInputStream ois = null;
    ObjectOutputStream oos = null;
    private Socket soc;
    private Server server;
    private long lastTime = 0;

    public boolean isLoggedIn = false;
    public String username;

    public ArrayList<ServerResponse> notificationStack = new ArrayList<>();
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

    public synchronized void sendMessage(ServerResponse toSend){
        try {
            oos.writeObject(Utilities.encrypt(toSend));
            soc.getOutputStream().flush();
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
                    String ip = soc.getRemoteSocketAddress().toString().substring(0, soc.getRemoteSocketAddress().toString().indexOf(":"));
                    ip = ip.replace("/", "");
                    if(ip.equals("127.0.0.1")){
                        URL whatismyip = new URL("http://checkip.amazonaws.com");
                        BufferedReader in = new BufferedReader(new InputStreamReader(
                                whatismyip.openStream()));

                        ip = in.readLine(); //you get the IP as a String
                        System.out.println(ip);
                    }
                    ServerRequest req = new ServerRequest(line, ip, soc.getPort());
                    server.respond(req, this);

                    // Disconnect Request, end thread
                    if(req.getRequestType().equals(RequestType.DISCONNECT)){
                        break;
                    }
                }
                else{
                    if(notificationStack.size() > 0){
                        sendMessage(notificationStack.remove(0));
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
