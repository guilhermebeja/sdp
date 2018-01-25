package Server.Contexts;

import Entities.User;
import Server.Database;
import Server.Parameters;
import Server.ServerResponse;
import Server.StatusCode;

import java.util.Optional;

public class PostUserRequestFriend implements ResponseContext{

    @Override
    public ServerResponse getResponse(Parameters params) {
        if(!params.containsParameter("username")){
            return new ServerResponse(StatusCode.BAD_REQUEST, "Username not provided");
        }

        String username = params.getParameter("username").get(0);

        Optional<User> u = Database.getUserByUsername(username);

        if(u.isPresent()){
            for(String friend : params.getParameter("friend")){
                if(username.equals(friend)){
                    return new ServerResponse(StatusCode.FORBBIDEN, "You can't add yourself");
                }
                if(Database.getUserByUsername(friend).isPresent() ){
                    u.get().addPendingFriend(friend);
                    Database.getUserByUsername(friend).get().getPendingAccept().add(username);

                }
                else {
                    return new ServerResponse(StatusCode.NOT_FOUND, "Friend \"" +friend+ "\" not found");
                }
            }

            return new ServerResponse(StatusCode.OK, "Friend request sent");
        }

        else{
            return new ServerResponse(StatusCode.NOT_FOUND, "User \"" + username + "\" not found");
        }
    }
}
