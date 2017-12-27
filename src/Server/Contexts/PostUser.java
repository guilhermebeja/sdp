package Server.Contexts;

import Entities.User;
import Server.Database;
import Server.Headers;
import Server.ServerResponse;
import Server.StatusCode;

public class PostUser implements ResponseContext {
    @Override
    public ServerResponse getResponse(Headers params) {
        String nickname = params.getHeader("username").get(0);
        String password = params.getHeader("password").get(0);
        String ip = params.getHeader("ip").get(0);
        String port = params.getHeader("port").get(0);

        User u = new User(nickname, password, ip, Integer.parseInt(port));

        if(Database.getUser(user -> user.getNickname().equals(u.getNickname())).size()==0){
            Database.addUser(u);
        }
        else{
            return new ServerResponse(StatusCode.FORBBIDEN, "Username Taken!");

        }
        return new ServerResponse(StatusCode.OK, "User Created!");
    }
}
