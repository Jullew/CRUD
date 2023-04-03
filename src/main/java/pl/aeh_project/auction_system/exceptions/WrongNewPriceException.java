package pl.aeh_project.auction_system.exceptions;

public class WrongNewPriceException extends RuntimeException{
    public WrongNewPriceException() {
        super("New price is lower than or equals to the current price");
    }
}
