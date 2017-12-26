package Server;

import DataStructures.Sendable;

import java.io.*;
import java.net.DatagramPacket;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket serverSoc;
    private boolean listen = false;

    public Server(ServerSocket serverSocket) {
        this.serverSoc = serverSocket;
        this.listen = true;
    }

    public void serve_requests(){

        try{
            serverSoc = new ServerSocket(8081);
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

    public void reply(Socket soc){
        try{

            ObjectInputStream oos = new ObjectInputStream(soc.getInputStream());
            Object o = oos.readObject();
            System.out.println(o.toString());

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

}
