package pl.aeh_project.auction_system.exceptions;

public class EndOfAuctionException extends RuntimeException{
    public EndOfAuctionException() {
        super("This auction is over");
    }
}
