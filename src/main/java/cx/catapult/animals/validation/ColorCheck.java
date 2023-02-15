package cx.catapult.animals.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = ColorValidator.class)
@Documented
public @interface ColorCheck {

    String message() default "{Color.invalid}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
