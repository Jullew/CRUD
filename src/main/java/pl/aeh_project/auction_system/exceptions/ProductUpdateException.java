package pl.aeh_project.auction_system.exceptions;

public class ProductUpdateException extends RuntimeException{
    private final String message;

    public ProductUpdateException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
