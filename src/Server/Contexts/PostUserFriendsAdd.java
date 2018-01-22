package Server.Contexts;

import Entities.User;
import Server.Database;
import Server.Parameters;
import Server.ServerResponse;
import Server.StatusCode;

import java.util.Optional;

public class PostUserFriendsAdd implements ResponseContext{
    @Override
    public ServerResponse getResponse(Parameters params) {
        if(!params.containsParameter("username")){
            return new ServerResponse(StatusCode.BAD_REQUEST, "Username not provided");
        }

        String username = params.getParameter("username").get(0);

        Optional<User> u = Database.getUserByUsername(username);

        if(u.isPresent()){
            for(String friend : params.getParameter("friend")){
                if(Database.getUserByUsername(friend).isPresent()){
                    u.get().addFriend(friend);
                }
            }

            return new ServerResponse(StatusCode.OK, "Friends added");
        }

        else{
            return new ServerResponse(StatusCode.NOT_FOUND, "User \"" + username + "\" not found");
        }
    }
}
