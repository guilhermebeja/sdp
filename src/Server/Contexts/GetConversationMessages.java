package Server.Contexts;

import DataStructures.Conversation;
import Server.Database;
import Server.Parameters;
import Server.ServerResponse;
import Server.StatusCode;

import java.util.Optional;

public class GetConversationMessages implements ResponseContext{
    @Override
    public ServerResponse getResponse(Parameters params) {
        if(!params.containsParameter("id")){
            return new ServerResponse(StatusCode.BAD_REQUEST, "Conversation ID not provided");
        }

        String id = params.getParameter("id").get(0);

        Optional<Conversation> conv = Database.getConversationByID(id);

        if(conv.isPresent()){
            return new ServerResponse(StatusCode.OK, conv.get().getMessages());
        }
        else{
            return new ServerResponse(StatusCode.NOT_FOUND, "The conversation \"" + id + "\" doesn't exist");
        }
    }
}
