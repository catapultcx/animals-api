package cx.catapult.animals.domain;

import cx.catapult.animals.exceptions.UnsupportedAnimalTypeException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

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

    @Test
    void animal_whenToStringCalled_shouldOutputJson() {
        String id = UUID.randomUUID().toString();

        Animal animal = new Animal();
        assertThat(animal).isNotNull();

        animal.setId(id);
        animal.setType(Animal.AnimalType.PARROT);
        animal.setName("Carrot");
        animal.setDescription("Carrot Description");

        assertThat(animal.toString()).isEqualTo("{id:'"+id+"'" +
                ", name:'Carrot'" +
                ", description:'Carrot Description'" +
                ", type:PARROT" +
                "}");
    }
    @Test
    void animal_whenCreatedWithNullConstructor_shouldCreate() {
        Animal animal = new Animal();
        assertThat(animal).isNotNull();
    }

    @Test
    void animal_whenComparedWithOtherObjects_shouldWork() throws UnsupportedAnimalTypeException {
        Animal animal = Animal.from("cat", "Tom", "Jerry");
        Animal same = Animal.from("cat", "Tom", "Jerry");
        Animal different = Animal.from("cat", "Tomi", "Jerry");

        assertThat(animal).isEqualTo(same);
        assertThat(animal).isNotEqualTo(different);

        assertThat(animal.hashCode()).isEqualTo(same.hashCode());
        assertThat(animal.hashCode()).isNotEqualTo(different.hashCode());
    }

}
