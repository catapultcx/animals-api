package cx.catapult.animals.validation;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * A sample validator for css color restriction.
 * Not supporting latest CSS version
 */
public class ColorValidator implements ConstraintValidator<ColorCheck, String> {
    private static final Pattern pattern =
            Pattern.compile("^(#[a-f0-9]{6}|#[a-f0-9]{8}|#[a-f0-9]{3}|#[a-f0-9]{4}|rgb *\\( *[0-9]{1,3}%? *, *[0-9]{1,3}%? *, *[0-9]{1,3}%? *\\)|rgba *\\( *[0-9]{1,3}%? *, *[0-9]{1,3}%? *, *[0-9]{1,3}%? *, *[0-9]{1,3}%? *\\)|black|green|silver|gray|olive|white|yellow|maroon|navy|red|blue|purple|teal|fuchsia|aqua)$");
    @Override
    public void initialize(ColorCheck constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isEmpty(s)) return false;
        return pattern.matcher(s.toLowerCase()).matches();
    }
}
