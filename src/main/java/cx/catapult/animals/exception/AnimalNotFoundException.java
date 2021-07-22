package cx.catapult.animals.exception;

public class AnimalNotFoundException extends RuntimeException {
    private static final String MESSAGE = "Record not found";

    public AnimalNotFoundException() {
        super(MESSAGE);
    }

}
