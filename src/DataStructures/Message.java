package DataStructures;

import java.io.Serializable;

public class Message implements Serializable{
    private String sender; // username
    private int conversation; // Conversation ID
    private String content;
    private long time;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public int getConversation() {
        return conversation;
    }

    public void setConversation(int conversation) {
        this.conversation = conversation;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Message(String sender, int conversation, String content, long time) {
        this.sender = sender;
        this.conversation = conversation;
        this.content = content;
        this.time = time;
    }

}
