package Server.Contexts;

import DataStructures.Conversation;
import DataStructures.Message;
import Server.Database;
import Server.Parameters;
import Server.ServerResponse;
import Server.StatusCode;

import java.util.Optional;

public class PostConversationAddMessage implements ResponseContext {
    @Override
    public ServerResponse getResponse(Parameters params) {
        if(params.containsParameter("id") && params.containsParameter("sender") && params.containsParameter("content") && params.containsParameter("time")){
            String id = params.getParameter("id").get(0); // conversation id
            String sender = params.getParameter("sender").get(0);
            String content = params.getParameter("content").get(0);
            String time = params.getParameter("time").get(0);

            Optional<Conversation> c = Database.getConversationByID(id);

            if(c.isPresent()){
                c.get().addMessage(new Message(sender, id, content, Long.parseLong(time)));
            }

            else{
                return new ServerResponse(StatusCode.FORBBIDEN, "Conversation doesn't exist");
            }


            return new ServerResponse(StatusCode.OK, "Message Added");

        }
        return new ServerResponse(StatusCode.BAD_REQUEST, "Some parameters not found");
    }
}
