package Server;

public class ServerRequest {
    private RequestType requestType;
    private String path;
    private Headers headers;

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Headers getHeaders() {
        return headers;
    }

    public void setHeaders(Headers headers) {
        this.headers = headers;
    }

    public ServerRequest(RequestType requestType, String path, Headers headers) {
        this.requestType = requestType;
        this.path = path;
        this.headers = headers;
    }
}
