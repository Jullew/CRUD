package pl.aeh_project.auction_system.exceptions;

public class NoProductException extends RuntimeException{
    public NoProductException() {
        super("There is no product with the given id");
    }
}
