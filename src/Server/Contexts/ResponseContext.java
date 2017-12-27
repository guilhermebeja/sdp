package Server.Contexts;

import Server.Headers;
import Server.ServerRequest;
import Server.ServerResponse;

public interface ResponseContext {
    ServerResponse getResponse(Headers params);
}
