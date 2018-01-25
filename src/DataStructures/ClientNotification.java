package DataStructures;

import java.io.Serializable;


public class ClientNotification implements Serializable {

    private boolean newFriendRequest;

    public boolean isNewFriendRequest() {
        return newFriendRequest;
    }

    public void setNewFriendRequest(boolean newFriendRequest) {
        this.newFriendRequest = newFriendRequest;
    }

    public ClientNotification(boolean newFriendRequest) {
        this.newFriendRequest = newFriendRequest;
    }
}
