package Server.Contexts;

import Entities.User;
import Server.*;

import java.util.Optional;

/**
 * Gets the user data by nickname
 */
public class GetUserIP extends ResponseContext {
    public GetUserIP(Server server) {
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
            return new ServerResponse(StatusCode.OK, u.get().getNetAddress());
        }

        else{
            return new ServerResponse(StatusCode.NOT_FOUND, "User \"" + username + "\" not found");
        }

    }
}
