package Interfaces;

import DataStructures.Conversation;
import DataStructures.Message;

public interface Observer {
    void newReceivedFriendRequest(Object o);
    void newSentFriendRequest(Object o);
    void friendRequestAccepted(Object o);
    void updateFriendList(Object o);
    void removedFriend(Object o);
    void changeCurrentConversation(Conversation c);
    void newMessage(int convID, Message m);
    void openSecretConversation(String ip);
}
