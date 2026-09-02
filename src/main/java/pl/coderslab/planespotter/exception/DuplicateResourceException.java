package pl.coderslab.planespotter.exception;

public class DuplicateResourceException extends RuntimeException{

    public DuplicateResourceException (String message){
        super(message);
    }
}
