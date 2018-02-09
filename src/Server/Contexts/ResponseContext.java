package Server.Contexts;

import Server.*;

public abstract class ResponseContext {

    protected Server server;

    public ResponseContext(Server server){
        this.server = server;
    }

    public abstract ServerResponse getResponse(Parameters params, ClientSocket clientSocket);
}
