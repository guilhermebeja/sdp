package Server;

import Server.Exceptions.RequestNotValidException;

import java.io.Serializable;

public class ServerRequest implements Serializable{
    private RequestType requestType;
    private String path[];
    private Parameters parameters;

    //region Getters and Setters
    public RequestType getRequestType() {
        return requestType;
    }
    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public String[] getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path.split("/");
    }

    public Parameters getParameters() {
        return parameters;
    }
    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }

    //endregion

    //region Constructors
    public ServerRequest(String url, String ip, int port) throws RequestNotValidException{
        parameters = new Parameters();

        String[] parts = url.split(" ");

        if(parts.length < 2){
            //TODO: Throw exception
        }

        String type = parts[0];

        if(type.equals("GET")){
            requestType = RequestType.GET;
        }
        else if(type.equals("POST")){
            requestType = RequestType.POST;
        }
        else if(type.equals("DISCONNECT")){
            requestType = RequestType.DISCONNECT;
        }
        else{
            throw new RequestNotValidException("Type \""+type+"\" not valid");
        }

        path = parts[1].split("/");

        String query=null;
        if(parts.length==3){
            query = parts[2];
        }

        if(query!=null){
            String params[] = query.split("&"); // [key1=value, key1=value, key2 = value]

            for(int i =0; i < params.length; i++){
                String q[] = params[i].split("=");
                if(q.length==2){
                    parameters.addParameter(q[0], q[1]);
                }
            }
        }

        parameters.addParameter("ip", ip);
        parameters.addParameter("port", Integer.toString(port));

    }

    //endregion

}
