package Server.Contexts;

import Entities.User;
import Server.*;

import java.util.Optional;

public class Logout extends ResponseContext{
    public Logout(Server server) {
        super(server);
    }

    @Override
    public ServerResponse getResponse(Parameters params, ClientSocket clientSocket) {
        if(!params.containsParameter("username")){
            return new ServerResponse(StatusCode.BAD_REQUEST, "Missing parameters");
        }

        String username = params.getParameter("username").get(0);

        Optional<User> u = Database.getUserByUsername(username);

        if(u.isPresent()){
            u.get().setLoggedIn(false);
            server.usersLogged.remove(username);
            return new ServerResponse(StatusCode.OK, "Logged Out");
        }

        else{
            return new ServerResponse(StatusCode.FORBBIDEN, "Invalid Credentials");
        }

    }
}
