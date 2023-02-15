package cx.catapult.animals.domain;

import cx.catapult.animals.service.AnimalService;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class AnimalTest {

    private final AnimalService catsService = new AnimalService("cat", Group.MAMMALS);
    @Test
    void animal_whenASupportedTypeProvided_shouldCreateAnimalInstance() {
        Animal animal = catsService.create(new Animal("Tom", "Tom and jerry", "blue"));
        assertThat(animal.getType()).isEqualTo("cat");
        assertThat(animal.getName()).isEqualTo("Tom");
        assertThat(animal.getDescription()).isEqualTo("Tom and jerry");
        assertThat(animal.getGroup()).isEqualTo(Group.MAMMALS);
    }

    @Test
    void animal_whenToStringCalled_shouldOutputJson() {
        String id = UUID.randomUUID().toString();

        Animal animal = new Animal();
        assertThat(animal).isNotNull();

        animal.setId(id);
        animal.setType("PARROT");
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
    void animal_whenComparedWithOtherObjects_shouldWork() {
        Animal animal = new Animal("Tom", "Jerry", "blue");
        Animal same = new Animal("Tom", "Jerry", "blue");
        Animal different = new Animal("Tomi", "Jerry", "blue");
        Stream.of(animal, same, different).forEach(it -> {
            it.setGroup(Group.MAMMALS);
            it.setType("cat");
        });

        assertThat(animal).isEqualTo(same);
        assertThat(animal).isNotEqualTo(different);

        assertThat(animal.hashCode()).isEqualTo(same.hashCode());
        assertThat(animal.hashCode()).isNotEqualTo(different.hashCode());
    }

}
