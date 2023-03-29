package pl.aeh_project.auction_system.exceptions;

public class LoggedUserException extends RuntimeException{
    public LoggedUserException(){
        super("User cannot be registered because hs is logged in");
    }
}
