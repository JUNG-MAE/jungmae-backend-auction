package jungmae.auction.exception;

public class SpecificException extends Exception {
    public SpecificException(String message) {
        super(message);
    }

    public SpecificException(String message, Throwable cause) {
        super(message, cause);
    }
}
