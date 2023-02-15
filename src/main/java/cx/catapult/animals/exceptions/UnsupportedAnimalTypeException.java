package cx.catapult.animals.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UnsupportedAnimalTypeException extends ResponseStatusException {
    public UnsupportedAnimalTypeException(String type) {
        super(HttpStatus.BAD_REQUEST, String.format("Animal type %s is not supported.", type));
    }
}
