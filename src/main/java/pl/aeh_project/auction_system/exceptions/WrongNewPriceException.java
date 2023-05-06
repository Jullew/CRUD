package pl.aeh_project.auction_system.exceptions;

public class WrongNewPriceException extends RuntimeException{

    private final String message;
    public WrongNewPriceException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
