package Server.Contexts;

import Entities.User;
import Server.Database;
import Server.Parameters;
import Server.ServerResponse;
import Server.StatusCode;

import java.util.ArrayList;
import java.util.Optional;

public class GetUserFriends implements ResponseContext {

    @Override
    public ServerResponse getResponse(Parameters params) {
        if(!params.containsParameter("username")){
            return new ServerResponse(StatusCode.BAD_REQUEST, "Username not provided");
        }

        String username = params.getParameter("username").get(0);

        Optional<User> u = Database.getUserByUsername(username);

        if(u.isPresent()){
            ArrayList<String> test = u.get().getFriends();
            return new ServerResponse(StatusCode.OK, test);
        }

        else{
            return new ServerResponse(StatusCode.NOT_FOUND, "User \"" + username + "\" not found");
        }
    }
}
