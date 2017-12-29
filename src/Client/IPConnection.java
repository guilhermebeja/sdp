package Client;

import java.io.Serializable;

public class IPConnection implements Serializable{
    private String senderIP, receiverIP;
    private int senderPort, receiverPort;

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
    //endregion

    //region Constructors
    public IPConnection(String senderIP, String receiverIP, int senderPort, int receiverPort) {
        this.senderIP = senderIP;
        this.receiverIP = receiverIP;
        this.senderPort = senderPort;
        this.receiverPort = receiverPort;
    }
    //endregion
}
