package Server.Contexts;

import Entities.User;
import Server.Database;
import Server.Parameters;
import Server.ServerResponse;
import Server.StatusCode;

import java.util.Optional;

public class Logout implements ResponseContext{
    @Override
    public ServerResponse getResponse(Parameters params) {
        if(!params.containsParameter("username")){
            return new ServerResponse(StatusCode.BAD_REQUEST, "Missing parameters");
        }

        String username = params.getParameter("username").get(0);

        Optional<User> u = Database.getUserByUsername(username);

        if(u.isPresent()){
            u.get().setLoggedIn(false);
            return new ServerResponse(StatusCode.OK, "Logged Out");
        }

        else{
            return new ServerResponse(StatusCode.FORBBIDEN, "Invalid Credentials");
        }

    }
}
