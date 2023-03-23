package pl.aeh_project.auction_system.exceptions;

public class ExpiredSessionException extends RuntimeException{
    public ExpiredSessionException(){
        super("Session has expired");
    }
}
