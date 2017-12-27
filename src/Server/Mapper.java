package Server;

import Server.Contexts.ResponseContext;

import java.util.HashMap;

public class Mapper {
    private HashMap<RequestType, HashMap<String, ResponseContext>> map;

    public Mapper(){
        map = new HashMap<>();
    }

    public void registerRoute(RequestType type, String path, ResponseContext toExecute){
        HashMap<String, ResponseContext> temp = new HashMap<>();
        if(map.containsKey(type)){
            temp = map.get(type);
        }
        temp.put(path, toExecute);
        map.put(type, temp);
    }

    public ResponseContext getContext(ServerRequest req){
        if(map.containsKey(req.getRequestType()) && map.get(req.getRequestType()).containsKey(req.getPath())){
            return map.get(req.getRequestType()).get(req.getPath());
        }
        else{
            return null;
        }
    }
}
