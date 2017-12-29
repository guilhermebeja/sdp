package Server;

import java.io.Serializable;

public class ServerResponse implements Serializable{
    private StatusCode statusCode;
    private Object response;

    //region Getters and Setters
    public StatusCode getStatusCode() {
        return statusCode;
    }
    public void setStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public Object getResponse() {
        return response;
    }
    public void setResponse(Object response) {
        this.response = response;
    }
    //endregion

    //region Constructors
    public ServerResponse(StatusCode statusCode, Object response) {
        this.statusCode = statusCode;
        this.response = response;
    }
    //endregion
}
