package pl.aeh_project.auction_system.exceptions;

public class EndOfAuctionException extends RuntimeException{

    private final String message;

    public EndOfAuctionException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
