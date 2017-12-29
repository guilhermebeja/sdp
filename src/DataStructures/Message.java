package DataStructures;

import Client.IPConnection;

import java.io.Serializable;

public class Message implements Sendable, Serializable{
    private IPConnection connection;
    private long time;
    private String message;

    //region Getters and Setters
    public String getSenderIP() {
        return connection.getSenderIP();
    }
    public void setSenderIP(String senderIP) {
        this.connection.setSenderIP(senderIP);
    }

    public String getReceiverIP() {
        return connection.getReceiverIP();
    }
    public void setReceiverIP(String receiverIP) {
        this.connection.setReceiverIP(receiverIP);
    }

    public int getSenderPort() {
        return connection.getSenderPort();
    }
    public void setSenderPort(int senderPort) {
        connection.setSenderPort(senderPort);
    }

    public int getReceiverPort() {
        return connection.getReceiverPort();
    }
    public void setReceiverPort(int receiverPort) {
        connection.setReceiverPort(receiverPort);
    }

    public long getTime() {
        return time;
    }
    public void setTime(long time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public Object getContent() {
        return message;
    }
    //endregion

    //region Constructors
    public Message(String senderIP, String receiverIP, int senderPort, int receiverPort, String message) {
        connection = new IPConnection(senderIP, receiverIP, senderPort, receiverPort);

        this.time = System.currentTimeMillis();
        this.message = message;
    }
    //endregion

    //region Methods
    @Override
    public int length() {
        return this.length();
    }
    //endregion
}
