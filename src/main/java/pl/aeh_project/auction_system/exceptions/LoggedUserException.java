package pl.aeh_project.auction_system.exceptions;

public class LoggedUserException extends RuntimeException{

    private final String message;
    public LoggedUserException(String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
