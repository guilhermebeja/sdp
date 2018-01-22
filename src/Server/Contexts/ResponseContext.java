package Server.Contexts;

import Server.Parameters;
import Server.ServerResponse;

public interface ResponseContext {
    ServerResponse getResponse(Parameters params);
}
