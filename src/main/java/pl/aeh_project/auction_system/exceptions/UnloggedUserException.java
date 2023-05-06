package pl.aeh_project.auction_system.exceptions;

public class UnloggedUserException extends RuntimeException{

    private final String message;
    public UnloggedUserException(String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
