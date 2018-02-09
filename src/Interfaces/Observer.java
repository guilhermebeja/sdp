package Interfaces;

public interface Observer {
    void newReceivedFriendRequest(Object o);
    void newSentFriendRequest(Object o);
    void friendRequestAccepted(Object o);
    void updateFriendList(Object o);
}
