package Server;

import Server.Contexts.ResponseContext;

import java.util.HashMap;

public class Mapper {
    private HashMap<RequestType, HashMap<String[], ResponseContext>> map;

    public Mapper(){
        map = new HashMap<>();
    }

    public void registerRoute(RequestType type, String path, ResponseContext toExecute){
        HashMap<String[], ResponseContext> temp = new HashMap<>();
        if(map.containsKey(type)){
            temp = map.get(type);
        }
        temp.put(path.split("/"), toExecute);
        map.put(type, temp);
    }

    public ResponseContext getContext(ServerRequest req){
        if(map.containsKey(req.getRequestType())){
            HashMap<String[], ResponseContext> temp = map.get(req.getRequestType());
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
                        return temp.get(key);
                    }
                }
            }
        }
        return null;
    }
}
