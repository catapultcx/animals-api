package cx.catapult.animals.validation;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ColorValidatorTest {
    private final ColorValidator validator = new ColorValidator();
    @ParameterizedTest
    @CsvSource({
            "blue,true",
            "red,true",
            "green,true",
            "gray,true",
            "black,true",
            "yellow,true",
            "orange,false",
            "#CC1245,true",
            "#00000000,true",
            "#ababab,true",
            "#ababab00,true",
            "#CC12,true",
            "#CC,false",
            "123456,false",
            ",false",
            "'',false",
            "      ,false"
    })
    void isValid(String color, boolean result) {
        assertThat(validator.isValid(color, null)).isEqualTo(result);
    }
}
