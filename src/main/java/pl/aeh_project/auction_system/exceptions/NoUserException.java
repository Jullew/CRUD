package pl.aeh_project.auction_system.exceptions;

public class NoUserException extends RuntimeException{
    public NoUserException() {
        super("There is no user");
    }
}
