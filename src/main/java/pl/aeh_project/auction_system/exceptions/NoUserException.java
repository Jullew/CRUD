package pl.aeh_project.auction_system.exceptions;

public class NoUserException extends RuntimeException{

    private final String message;
    public NoUserException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
