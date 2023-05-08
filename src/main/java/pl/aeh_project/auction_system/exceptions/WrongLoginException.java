package pl.aeh_project.auction_system.exceptions;

public class WrongLoginException extends RuntimeException {
    private final String message;

    public WrongLoginException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
