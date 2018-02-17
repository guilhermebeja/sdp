package Server;

import java.io.Serializable;

public class Notification implements Serializable{
    public enum NotificationType {
        NEW_FRIEND_REQUEST,
        FRIEND_REQUEST_ACCEPTED,
        FRIEND_REQUEST_REJECTED,
        NEW_MESSAGE
    }

    public NotificationType type;
    public Object data;

    public Notification(NotificationType type, Object data) {
        this.type = type;
        this.data = data;
    }
}
