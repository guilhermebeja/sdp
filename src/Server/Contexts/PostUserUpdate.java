package Server.Contexts;

import Entities.User;
import Server.*;

import java.util.Optional;

public class PostUserUpdate extends ResponseContext {

    public PostUserUpdate(Server server) {
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
            if(params.containsParameter("newUsername")){
                String newUsername = params.getParameter("newUsername").get(0);

                if(Database.containsUser(user -> user.getUsername().equals(newUsername))){
                    u.get().setUsername(newUsername);

                    //TODO: Logout user when username changes
                    return new ServerResponse(StatusCode.OK, "Username change");
                }
                return new ServerResponse(StatusCode.FORBBIDEN, "Username taken");

            }
            else if(params.containsParameter("newPassword")){
                String newPassword = params.getParameter("newPassword").get(0);
                u.get().setPassword(newPassword);

                //TODO: Logout user when password changes
                return new ServerResponse(StatusCode.OK, "Password changed");
            }
            else if(params.containsParameter("newEmail")){
                String newEmail = params.getParameter("newEmail").get(0);
                //u.get().setEmail(newEmail);
                //TODO: implemnte whend email is created
                return new ServerResponse(StatusCode.OK, "Email chaged");
            }
            else {
                return new ServerResponse(StatusCode.BAD_REQUEST, "Nothing happens");
            }
        }

        return new ServerResponse(StatusCode.NOT_FOUND, "User not found");
    }
}
