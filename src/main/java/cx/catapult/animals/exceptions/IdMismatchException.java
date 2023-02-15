package cx.catapult.animals.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class IdMismatchException extends ResponseStatusException {
    public IdMismatchException() {
        super(HttpStatus.BAD_REQUEST, "Request id and model id is not matching");
    }
}
