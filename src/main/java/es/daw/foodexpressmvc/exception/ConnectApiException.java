package es.daw.foodexpressmvc.exception;

public class ConnectApiException extends RuntimeException {
    public ConnectApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
