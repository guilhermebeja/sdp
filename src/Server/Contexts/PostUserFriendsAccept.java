package Server.Contexts;

import Entities.User;
import Server.*;

import java.util.Optional;

public class PostUserFriendsAccept extends ResponseContext{
    public PostUserFriendsAccept(Server server) {
        super(server);
    }

    @Override
    public ServerResponse getResponse(Parameters params, ClientSocket clientSocket) {
        if(!params.containsParameter("username") || !params.containsParameter("friend")){
            return new ServerResponse(StatusCode.BAD_REQUEST, "Username not provided");
        }

        String username = params.getParameter("username").get(0);

        Optional<User> u = Database.getUserByUsername(username);
        String friend = params.getParameter("friend").get(0);

        if(u.isPresent()){
            if(username.equals(friend)){
                return new ServerResponse(StatusCode.FORBBIDEN, "You can't add yourself");
            }
            if(Database.getUserByUsername(friend).isPresent()){
                if(u.get().getPendingAccept().contains(friend)){
                    if(Database.getUserByUsername(friend).get().getPendingFriends().contains(username)){
                        u.get().getFriends().add(friend);
                        u.get().getPendingAccept().remove(friend);
                        Database.getUserByUsername(friend).get().getFriends().add(username);
                        Database.getUserByUsername(friend).get().getPendingFriends().remove(username);

                        server.sendNotification(friend, new ServerResponse(StatusCode.NOTIFICATION, new Notification(Notification.NotificationType.FRIEND_REQUEST_ACCEPTED, username)));

                        return new ServerResponse(StatusCode.OK, "Friend's Added");
                    }
                }
                return new ServerResponse(StatusCode.ERROR, "Server Error");
            }
            else {
                return new ServerResponse(StatusCode.NOT_FOUND, "Friend \"" +friend+ "\" not found");
            }
        }

        else{
            return new ServerResponse(StatusCode.NOT_FOUND, "User \"" + username + "\" not found");
        }
    }
}
