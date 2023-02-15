package cx.catapult.animals.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({ OperationNotAllowedException.class })
    public ResponseEntity<ErrorResponse> handleException(OperationNotAllowedException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()),  HttpStatus.BAD_REQUEST);
    }
}
