package Server.Contexts;

import Entities.User;
import Server.Database;
import Server.Parameters;
import Server.ServerResponse;
import Server.StatusCode;

import java.util.Optional;

public class Login implements ResponseContext{
    @Override
    public ServerResponse getResponse(Parameters params) {
        if(!params.containsParameter("username") || !params.containsParameter("password")){
            return new ServerResponse(StatusCode.BAD_REQUEST, "Missing parameters");
        }

        String username = params.getParameter("username").get(0);
        String password = params.getParameter("password").get(0);

        Optional<User> u = Database.getUserByUsername(username);

        if(u.isPresent()){
            if(!u.get().getPassword().equals(password)){
                return new ServerResponse(StatusCode.FORBBIDEN, "Invalid Credentials");
            }
            else{
                u.get().setLoggedIn(true);
                u.get().setIp(params.getParameter("ip").get(0));
                u.get().setPort(Integer.parseInt(params.getParameter("port").get(0)));
                return new ServerResponse(StatusCode.OK, "Logged In");
            }
        }

        else{
            return new ServerResponse(StatusCode.FORBBIDEN, "Invalid Credentials");
        }

    }
}
