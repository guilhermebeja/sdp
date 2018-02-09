package Server;

import Entities.User;
import Extras.Pair;
import Server.Contexts.ResponseContext;

import java.util.HashMap;
import java.util.Optional;

public class Mapper {
    private HashMap<RequestType, HashMap<String[], Pair<ResponseContext, Boolean>>> map;

    public Mapper(){
        map = new HashMap<>();
    }

    public void registerRoute(RequestType type, String path, ResponseContext toExecute, boolean authentication){
        HashMap<String[], Pair<ResponseContext, Boolean>> temp = new HashMap<>();

        if(map.containsKey(type)){
            temp = map.get(type);
        }
        temp.put(path.split("/"), new Pair<ResponseContext, Boolean>(toExecute, authentication));
        map.put(type, temp);
    }

    public ResponseContext getContext(ServerRequest req){
        if(map.containsKey(req.getRequestType())){
            HashMap<String[], Pair<ResponseContext, Boolean>> temp = map.get(req.getRequestType());
            for(String[] key : temp.keySet()){
                if(key.length != req.getPath().length){
                    continue;
                }
                for(int i = 0; i < key.length; i++){
                    if(key[i].contains(":")){
                        req.getParameters().addParameter(key[i].replace(":", ""), req.getPath()[i]);
                    }
                    else{
                        if(!key[i].equals(req.getPath()[i])){
                            break;
                        }
                    }

                    if(i == key.length-1){
                        if(temp.get(key).getRight()){
                            Optional<User> o = Database.getUsers().stream().filter(u -> u.getIp().equals(req.getParameters().getParameter("ip").get(0))).findFirst();
                            if(o.isPresent() && o.get().isLoggedIn()) {
                                return temp.get(key).getLeft();
                            }
                            return null;
                        }
                        return temp.get(key).getLeft();
                    }
                }
            }
        }
        return null;
    }
}
