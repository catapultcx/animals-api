package cx.catapult.animals.exception;

import cx.catapult.animals.domain.ApiError;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

@RestControllerAdvice
@Order(HIGHEST_PRECEDENCE)
public class ApiExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        ApiError apiError = getApiError(fieldErrors.get(0).getDefaultMessage());
        return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolationException(
            ConstraintViolationException ex) {
        ConstraintViolation constraintViolation = ex.getConstraintViolations().stream().findAny().get();
        ApiError apiError = getApiError(constraintViolation.getMessage());
        return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler(AnimalNotFoundException.class)
    public ResponseEntity handleNotFoundException(
            AnimalNotFoundException ex) {
        ApiError apiError = getApiError(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    private ApiError getApiError(String message) {
        ApiError apiError = new ApiError();
        apiError.setMessage(message);
        return apiError;
    }
}
