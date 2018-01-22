package Server.Contexts;

import DataStructures.Conversation;
import Server.Database;
import Server.Parameters;
import Server.ServerResponse;
import Server.StatusCode;

import java.util.Optional;

public class PostConversationRemoveUser implements ResponseContext{
    @Override
    public ServerResponse getResponse(Parameters params) {
        if(params.containsParameter("id") && params.containsParameter("username")){
            String id = params.getParameter("id").get(0); // conversation id

            Optional<Conversation> c = Database.getConversationByID(id);

            if(c.isPresent()){
                for(String u : params.getParameter("username")){
                    c.get().removeUser(u);
                }
            }

            else{
                return new ServerResponse(StatusCode.FORBBIDEN, "Conversation doesn't exist");
            }

            return new ServerResponse(StatusCode.OK, "Users added to conversation");

        }
        return new ServerResponse(StatusCode.BAD_REQUEST, "Some parameters not found");
    }
}
