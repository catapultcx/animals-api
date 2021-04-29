package cx.catapult.animals.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ApplicationExceptionHandler {

  @ExceptionHandler(DataNotFoundException.class)
  public ResponseEntity<String> handleEntityNotFoundException(DataNotFoundException e) {
    log.warn(e.getMessage());
    return ResponseEntity.status(NOT_FOUND).body(String.format(e.getMessage()));
  }
}
