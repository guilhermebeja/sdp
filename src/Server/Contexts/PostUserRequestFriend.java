package Server.Contexts;

import Entities.User;
import Server.*;

import java.util.Optional;

public class PostUserRequestFriend extends ResponseContext{

    public PostUserRequestFriend(Server server) {
        super(server);
    }

    @Override
    public ServerResponse getResponse(Parameters params, ClientSocket clientSocket) {
        if(!params.containsParameter("username")){
            return new ServerResponse(StatusCode.BAD_REQUEST, "Username not provided");
        }

        String username = params.getParameter("username").get(0);

        Optional<User> u = Database.getUserByUsername(username);
        String friend = params.getParameter("friend").get(0);

        if(u.isPresent()){
            if(username.equals(friend)){
                return new ServerResponse(StatusCode.FORBBIDEN, "You can't add yourself");
            }
            if(Database.getUserByUsername(friend).isPresent() ){
                if(!u.get().getPendingFriends().contains(friend)){
                    u.get().addPendingFriend(friend);
                }
                if(!Database.getUserByUsername(friend).get().getPendingAccept().contains(username)){
                    Database.getUserByUsername(friend).get().getPendingAccept().add(username);
                }
            }
            else {
                return new ServerResponse(StatusCode.NOT_FOUND, "Friend \"" +friend+ "\" not found");
            }

            server.sendNotification(friend, new ServerResponse(StatusCode.NOTIFICATION, new Notification(Notification.NotificationType.NEW_FRIEND_REQUEST, username)));
            return new ServerResponse(StatusCode.OK, "Friend request sent");
        }

        else{
            return new ServerResponse(StatusCode.NOT_FOUND, "User \"" + username + "\" not found");
        }
    }
}
