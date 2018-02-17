package Server.Contexts;

import Entities.User;
import Server.*;

import java.util.Optional;

public class PostUserFriendsRemove extends ResponseContext {
    public PostUserFriendsRemove(Server server) {
        super(server);
    }

    @Override
    public ServerResponse getResponse(Parameters params, ClientSocket clientSocket) {
        if(!params.containsParameter("username")){
            return new ServerResponse(StatusCode.BAD_REQUEST, "Username not provided");
        }

        String username = params.getParameter("username").get(0);
        String friend = params.getParameter("friend").get(0);
        Optional<User> u = Database.getUserByUsername(username);
        Optional<User> f = Database.getUserByUsername(friend);

        if(u.isPresent()){

            if(u.get().getFriends().contains(friend)){
                u.get().removeFriend(friend);
            }
            if(f.isPresent()){
                f.get().removeFriend(username);
                server.sendNotification(friend, new ServerResponse(StatusCode.NOTIFICATION, new Notification(Notification.NotificationType.FRIEND_REMOVED, username)));
            }
            return new ServerResponse(StatusCode.OK, "Friends Removed");
        }

        else{
            return new ServerResponse(StatusCode.NOT_FOUND, "User \"" + username + "\" not found");
        }
    }
}
