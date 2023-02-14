package cx.catapult.animals.exceptions;

public class UnsupportedAnimalTypeException extends Exception {
    public UnsupportedAnimalTypeException(String type) {
        super(String.format("Animal type %s is not supported.", type));
    }
}
