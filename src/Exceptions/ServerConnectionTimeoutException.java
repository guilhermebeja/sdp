package Exceptions;

public class ServerConnectionTimeoutException extends ClientException {
    public ServerConnectionTimeoutException() {
    }

    public ServerConnectionTimeoutException(String message) {
        super(message);
    }
}
