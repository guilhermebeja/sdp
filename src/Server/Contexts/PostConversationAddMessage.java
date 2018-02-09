package Server.Contexts;

import DataStructures.Conversation;
import DataStructures.Message;
import Server.*;

import java.util.Optional;

public class PostConversationAddMessage extends ResponseContext {
    public PostConversationAddMessage(Server server) {
        super(server);
    }

    @Override
    public ServerResponse getResponse(Parameters params, ClientSocket clientSocket) {
        if(params.containsParameter("id") && params.containsParameter("sender") && params.containsParameter("content") && params.containsParameter("time")){
            int id = Integer.parseInt(params.getParameter("id").get(0));
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
