package Server.Contexts;

import DataStructures.Conversation;
import Server.*;

import java.util.Optional;

public class GetConversationByID extends ResponseContext{
    public GetConversationByID(Server server) {
        super(server);
    }

    @Override
    public ServerResponse getResponse(Parameters params, ClientSocket clientSocket) {
        if(!params.containsParameter("id")){
            return new ServerResponse(StatusCode.BAD_REQUEST, "Conversation ID not provided");
        }

        int id = Integer.parseInt(params.getParameter("id").get(0));

        Optional<Conversation> conv = Database.getConversationByID(id);

        if(conv.isPresent()){
            return new ServerResponse(StatusCode.OK, conv.get());
        }
        else{
            return new ServerResponse(StatusCode.NOT_FOUND, "The conversation \"" + id + "\" doesn't exist");
        }
    }
}

