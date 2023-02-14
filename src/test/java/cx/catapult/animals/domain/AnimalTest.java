package cx.catapult.animals.domain;

import cx.catapult.animals.exceptions.UnsupportedAnimalTypeException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class AnimalTest {

    @Test
    void animal_whenASupportedTypeProvided_shouldCreateAnimalInstance() throws UnsupportedAnimalTypeException {
        Animal animal = Animal.from("cat", "Tom", "Tom and jerry");
        assertThat(animal.getType()).isEqualTo("CAT");
        assertThat(animal.getName()).isEqualTo("Tom");
        assertThat(animal.getDescription()).isEqualTo("Tom and jerry");
        assertThat(animal.getGroup()).isEqualTo(Group.MAMMALS);
    }

    @Test
    void animal_whenAnUnsupportedTypeProvided_shouldThrowException() {
        assertThatExceptionOfType(UnsupportedAnimalTypeException.class)
                .isThrownBy(() -> Animal.from("whale", "Tom", "Tom and jerry"))
                .withMessage("Animal type whale is not supported.");
    }
}
