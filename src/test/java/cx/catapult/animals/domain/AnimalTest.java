package cx.catapult.animals.domain;

import cx.catapult.animals.repository.AnimalRepository;
import cx.catapult.animals.service.AnimalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class AnimalTest {

    @Autowired
    AnimalRepository repository;

    private AnimalService catsService;
    @BeforeEach
    public void setUp() {
        repository.deleteAll();
        catsService = new AnimalService("cat", Group.MAMMALS, repository);
    }
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
