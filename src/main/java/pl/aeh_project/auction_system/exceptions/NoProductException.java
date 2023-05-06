package pl.aeh_project.auction_system.exceptions;

public class NoProductException extends RuntimeException{

    private final String message;
    public NoProductException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
