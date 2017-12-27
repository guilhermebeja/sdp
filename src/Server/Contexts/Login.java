package Server.Contexts;

import Entities.User;
import Server.*;

public class Login implements ResponseContext {
    @Override
    public ServerResponse getResponse(Headers params) {
        String nickname = params.getHeader("username").get(0);
        String password = params.getHeader("password").get(0);
        String ip = params.getHeader("ip").get(0);
        String port = params.getHeader("port").get(0);

        if(Database.containsUser(user -> user.getNickname().equals(nickname))){
            User u = Database.getUser(user -> user.getNickname().equals(nickname)).get(0);
            if(u.getPassword().equals(password)){
                u.setIp(ip);
                u.setPort(Integer.parseInt(port));
                return new ServerResponse(StatusCode.OK, u);
            }
            else{
                return new ServerResponse(StatusCode.FORBBIDEN, "Wrong Password");
            }
        }
        else{
            return new ServerResponse(StatusCode.NOT_FOUND, "User not found");
        }
    }
}
