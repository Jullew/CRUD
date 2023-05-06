package pl.aeh_project.auction_system.exceptions;

public class ExpiredSessionException extends RuntimeException{

    private final String message;

    public ExpiredSessionException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
