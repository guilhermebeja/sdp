package Server.Contexts;

import Entities.User;
import Server.*;

import java.util.ArrayList;

/**
 * Gets the user data by nickname
 */
public class GetUser implements ResponseContext {
    @Override
    public ServerResponse getResponse(Headers params) {
        String toFind = params.getHeader("username").get(0);
        ArrayList<User> users = Database.getUser(user -> user.getNickname().equals(toFind));
        if(users.size() > 1){
            return new ServerResponse(StatusCode.ERROR, "Multiple Users Found");
        }

        if(users.size() == 0){
            return new ServerResponse(StatusCode.NOT_FOUND, "No users found");
        }

        else{
            return new ServerResponse(StatusCode.OK, users.get(0));
        }
    }
}
