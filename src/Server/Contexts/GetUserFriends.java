package Server.Contexts;

import Entities.User;
import Server.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class GetUserFriends extends ResponseContext {

    public GetUserFriends(Server server) {
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
            ArrayList<String> friends = u.get().getFriends();
            ArrayList<String> sent = u.get().getPendingFriends();
            ArrayList<String> received = u.get().getPendingAccept();
            HashMap<String, ArrayList<String>> result = new HashMap<>();

            result.put("friends", friends);
            result.put("requestSent", sent);
            result.put("requestReceived", received);

            System.out.println(sent.toString());

            return new ServerResponse(StatusCode.OK, result);
        }

        else{
            return new ServerResponse(StatusCode.NOT_FOUND, "User \"" + username + "\" not found");
        }
    }
}
