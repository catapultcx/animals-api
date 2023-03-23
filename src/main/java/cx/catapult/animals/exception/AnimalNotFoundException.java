package cx.catapult.animals.exception;

public class AnimalNotFoundException extends RuntimeException {
    public AnimalNotFoundException(String id) {
        super("Animal not found with id: " + id);
    }
}