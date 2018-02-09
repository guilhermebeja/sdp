package Server.Contexts;

import DataStructures.Conversation;
import Server.*;

public class PostConversationCreate extends ResponseContext{
    public PostConversationCreate(Server server) {
        super(server);
    }

    @Override
    public ServerResponse getResponse(Parameters params, ClientSocket clientSocket) {
        if(params.containsParameter("id") && params.containsParameter("username")){
            int id = Integer.parseInt(params.getParameter("id").get(0));

            if(Database.containsConversation(conv -> conv.getId()==id)){
                return new ServerResponse(StatusCode.FORBBIDEN, "Conversation already exists");
            }
            Conversation c = new Conversation(id);

            for(String username : params.getParameter("username")){
                c.addUser(username);
            }

            Database.addConversation(c);

            return new ServerResponse(StatusCode.OK, "Conversation created");

        }
        return new ServerResponse(StatusCode.BAD_REQUEST, "Some parameters not found");
    }
}
