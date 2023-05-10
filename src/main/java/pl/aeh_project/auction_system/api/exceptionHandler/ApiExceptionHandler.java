package pl.aeh_project.auction_system.api.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.aeh_project.auction_system.exceptions.*;

/* Przechwytywanie wyjątków z aplikacji */
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<Object> handle(WrongPasswordException wrongPasswordException){
        return new ResponseEntity<>(wrongPasswordException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OwnAuctionBidding.class)
    public ResponseEntity<Object> handle(OwnAuctionBidding ownAuctionBidding){
        return new ResponseEntity<>(ownAuctionBidding.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WrongLoginException.class)
    public ResponseEntity<Object> handle(WrongLoginException wrongLoginException){
        return new ResponseEntity<>(wrongLoginException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DoubleBiddingException.class)
    public ResponseEntity<Object> handle(DoubleBiddingException doubleBindingException){
        return new ResponseEntity<>(doubleBindingException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EndOfAuctionException.class)
    public ResponseEntity<Object> handle(EndOfAuctionException endOfAuctionException) {
        return new ResponseEntity<>(endOfAuctionException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExpiredSessionException.class)
    public ResponseEntity<Object> handle(ExpiredSessionException expiredSessionException) {
        return new ResponseEntity<>(expiredSessionException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LoggedUserException.class)
    public ResponseEntity<Object> handle(LoggedUserException loggedUserException) {
        return new ResponseEntity<>(loggedUserException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoProductException.class)
    public ResponseEntity<Object> handle(NoProductException noProductException) {
        return new ResponseEntity<>(noProductException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoUserException.class)
    public ResponseEntity<Object> handle(NoUserException noUserException) {
        return new ResponseEntity<>(noUserException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnloggedUserException.class)
    public ResponseEntity<Object> handle(UnloggedUserException unloggedUserException) {
        return new ResponseEntity<>(unloggedUserException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WrongNewPriceException.class)
    public ResponseEntity<Object> handle(WrongNewPriceException wrongNewPriceException) {
        return new ResponseEntity<>(wrongNewPriceException.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
