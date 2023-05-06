package pl.aeh_project.auction_system.exceptions;

public class DoubleBiddingException extends RuntimeException{
    private final String message;

    public DoubleBiddingException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
