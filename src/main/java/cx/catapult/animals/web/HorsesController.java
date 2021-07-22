package cx.catapult.animals.web;

import cx.catapult.animals.domain.ApiError;
import cx.catapult.animals.domain.Horse;
import cx.catapult.animals.service.HorseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(path = "/api/1/horses", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class HorsesController {

    @Autowired
    private HorseService service;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    Horse
    create(@RequestBody @Valid Horse horse) {
        return service.create(horse);
    }

    @GetMapping(value = "/{id}")
    public @ResponseBody
    Horse get(@PathVariable @Valid @NotBlank(message = "Id cannot be null") String id) {
        return service.get(id);
    }

    @GetMapping(value = "", produces = "application/json")
    public @ResponseBody
    Collection<Horse> all() {
        return service.all();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        ApiError apiError = new ApiError();
        apiError.setMessage(fieldErrors.get(0).getDefaultMessage());
        return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolationException(
            ConstraintViolationException ex) {
        ConstraintViolation constraintViolation = ex.getConstraintViolations().stream().findAny().get();
        ApiError apiError = new ApiError();
        apiError.setMessage(constraintViolation.getMessage());
        return ResponseEntity.badRequest().body(apiError);
    }
}
