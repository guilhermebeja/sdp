package Server;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;

public class ServerResponse implements Serializable{
    private StatusCode statusCode;
    private Object response;
    private int reqID = -1;

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

    public int getReqID() {
        return reqID;
    }

    public void setReqID(int reqID) {
        this.reqID = reqID;
    }

    //endregion

    //region Constructors
    public ServerResponse(StatusCode statusCode, Object response) {
        this.statusCode = statusCode;
        this.response = response;
    }

    @Override
    public String toString() {
        return "Status: " + statusCode + "\nResponse: " + response;
    }
    //endregion
}
