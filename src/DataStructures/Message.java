package DataStructures;

import java.io.Serializable;

public class Message implements Sendable, Serializable{
    private String senderIP, receiverIP;
    private int senderPort, receiverPort;
    private long time;
    private String message;

    //region Getters and Setters
    public String getSenderIP() {
        return senderIP;
    }
    public void setSenderIP(String senderIP) {
        this.senderIP = senderIP;
    }
    public String getReceiverIP() {
        return receiverIP;
    }
    public void setReceiverIP(String receiverIP) {
        this.receiverIP = receiverIP;
    }
    public int getSenderPort() {
        return senderPort;
    }
    public void setSenderPort(int senderPort) {
        this.senderPort = senderPort;
    }
    public int getReceiverPort() {
        return receiverPort;
    }
    public void setReceiverPort(int receiverPort) {
        this.receiverPort = receiverPort;
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
    //endregion

    //region Constructors
    public Message(String senderIP, String receiverIP, int senderPort, int receiverPort, String message) {
        this.senderIP = senderIP;
        this.receiverIP = receiverIP;
        this.senderPort = senderPort;
        this.receiverPort = receiverPort;
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
