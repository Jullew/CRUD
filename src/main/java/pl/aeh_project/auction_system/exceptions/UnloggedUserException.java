package pl.aeh_project.auction_system.exceptions;

public class UnloggedUserException extends RuntimeException{
    public UnloggedUserException(){
        super("User cannot be updated because he isn't logged in");
    }
}
