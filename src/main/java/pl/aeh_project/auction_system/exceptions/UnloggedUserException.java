package pl.aeh_project.auction_system.exceptions;

public class UnloggedUserException extends RuntimeException{
    public UnloggedUserException(){
        super("User cannot be updated because hs isn't logged in");
    }
}
