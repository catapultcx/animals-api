package cx.catapult.animals.exception;

public class AnimalServiceException extends RuntimeException {
    public AnimalServiceException(String message) {
        super(message);
    }
}