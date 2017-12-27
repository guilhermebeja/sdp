package Server;

import Client.IPConnection;
import DataStructures.Sendable;

import java.io.Serializable;

public class ServerRequest implements Sendable, Serializable{
    private RequestType requestType;
    private String path;
    private Headers headers;
    private IPConnection connection;

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Headers getHeaders() {
        return headers;
    }

    public void setHeaders(Headers headers) {
        this.headers = headers;
    }

    public ServerRequest(RequestType requestType, String path, Headers headers, IPConnection connection) {
        this.connection = connection;
        this.requestType = requestType;
        this.path = path;
        this.headers = headers;
    }

    @Override
    public int length() {
        return this.length();
    }

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

    @Override
    public Object getContent() {
        return null;
    }

    public void setReceiverPort(int receiverPort) {
        connection.setReceiverPort(receiverPort);
    }
}
