package pl.aeh_project.auction_system.exceptions;

public class WrongPasswordException extends RuntimeException{
    private final String message;

    public WrongPasswordException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}