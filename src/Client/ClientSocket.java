/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

/**
 *
 * @author HP
 */
import DataStructures.Message;
import DataStructures.Sendable;

import java.io.*;
import java.net.*;

public class ClientSocket extends Thread{
    private int port;
    private String ip;
    InetAddress ER, IPr;
    DatagramSocket DS;
    byte bp[] = new byte[1024];
    private Client client;

    public ClientSocket(int port, Client client){
        this.port = port;
        this.client = client;

    }

    public void run(){
        try{DS=new DatagramSocket(port);}
        catch(IOException e){}
        while(true) receiveDP();
    }

    public Sendable receiveDP(){
        try{
            DatagramPacket dp=new DatagramPacket(bp,1024);
            DS.receive(dp);
            IPr = dp.getAddress();
            byte payload[]=dp.getData();
            ByteArrayInputStream bais = new ByteArrayInputStream(payload);
            ObjectInputStream ois = new ObjectInputStream(bais);
            Sendable received = (Sendable)ois.readObject();

            if(received instanceof Message){
                client.onMessageReceive((Message)received);
            }
            int len=dp.getLength();

            return received;
        }catch(IOException e){
            System.out.println(e.getMessage());
        }catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void sendDP(Sendable msg){
        try{

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(msg);
            byte[] dataToSend = baos.toByteArray();

            ER=InetAddress.getByName(msg.getReceiverIP());
            DatagramPacket DP = new DatagramPacket(dataToSend,dataToSend.length,ER,port);
            DS.send(DP);
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}
