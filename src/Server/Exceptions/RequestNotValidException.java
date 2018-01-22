package Server.Exceptions;

public class RequestNotValidException extends Exception{
    public RequestNotValidException() {
    }

    public RequestNotValidException(String s) {
        super(s);
    }
}
