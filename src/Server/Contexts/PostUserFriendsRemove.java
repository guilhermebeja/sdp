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

        Optional<User> u = Database.getUserByUsername(username);

        if(u.isPresent()){
            for(String friend : params.getParameter("friend")){
                if(u.get().getFriends().contains(friend)){
                    u.get().removeFriend(friend);
                }
            }

            return new ServerResponse(StatusCode.OK, "Friends Removed");
        }

        else{
            return new ServerResponse(StatusCode.NOT_FOUND, "User \"" + username + "\" not found");
        }
    }
}
