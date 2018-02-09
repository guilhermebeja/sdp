package Server.Contexts;

import Entities.User;
import Server.*;

public class PostUserCreate extends ResponseContext {
    public PostUserCreate(Server server) {
        super(server);
    }

    @Override
    public ServerResponse getResponse(Parameters params, ClientSocket clientSocket) {
        if(params.containsParameter("username") && params.containsParameter("email") && params.containsParameter("password") && params.containsParameter("ip") && params.containsParameter("port")){
            String username = params.getParameter("username").get(0);
            String password = params.getParameter("password").get(0);
            String email = params.getParameter("email").get(0);
            String ip = params.getParameter("ip").get(0);
            int port = Integer.parseInt(params.getParameter("port").get(0)); // port must be a valid int or exception will be thrown

            if(Database.containsUser(user -> user.getUsername().equals(username))){
                return new ServerResponse(StatusCode.FORBBIDEN, "User \"" + username + "\" already exists.");
            }

            Database.addUser(new User(username, password, ip, port));
            return new ServerResponse(StatusCode.OK, "User created!");
        }
        return new ServerResponse(StatusCode.BAD_REQUEST, "Some parameters not found");
    }
}
