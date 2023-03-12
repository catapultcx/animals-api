package cx.catapult.animals.validator;

import java.util.Objects;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import cx.catapult.animals.domain.Cat;

@Component
public class CatValidator implements Validator {

    @Override
    public boolean supports(final Class<?> clazz) {
	return Cat.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(final Object target, final Errors errors) {
	final Cat cat = (Cat) target;
	if (Objects.isNull(cat.getName()) || cat.getName().isBlank()) {
	    errors.rejectValue("name", "Please enter valid name.");
	}
	if (Objects.isNull(cat.getDescription()) || cat.getDescription().isBlank()) {
	    errors.rejectValue("description", "Please enter valid description.");
	}
    }
}