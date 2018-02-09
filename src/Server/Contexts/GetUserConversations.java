package Server.Contexts;

import Server.*;

public class GetUserConversations extends ResponseContext{
    public GetUserConversations(Server server) {
        super(server);
    }

    @Override
    public ServerResponse getResponse(Parameters params, ClientSocket clientSocket) {
        if(!params.containsParameter("username")){
            return new ServerResponse(StatusCode.BAD_REQUEST, "Username not provided");
        }
        String username = params.getParameter("username").get(0);

        return new ServerResponse(StatusCode.OK, Database.getConversations(c -> c.getUsers().contains(username)));


    }
}


