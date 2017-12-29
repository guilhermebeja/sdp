package DataStructures;

import java.io.Serializable;

public interface Sendable {
    int length();
    String getSenderIP();
    int getSenderPort();
    String getReceiverIP();
    int getReceiverPort();
    Object getContent();
}
