package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AnimalServiceTest {

    AnimalService service = new AnimalService();
    Animal animal = new Animal("Tom", "Bob cat", "grey", "mammals");

    @Test
    public void createShouldWork() throws Exception {
        Animal thisAnimal = new Animal();
        thisAnimal.setName("Jerry");
        thisAnimal.setDescription("Mouse Cat");
        Animal actual = service.create(thisAnimal);
        assertThat(actual).isEqualTo(thisAnimal);
        assertThat(actual.getName()).isEqualTo(thisAnimal.getName());
        assertThat(actual.getDescription()).isEqualTo(thisAnimal.getDescription());
        assertThat(actual.getType()).isEqualTo(thisAnimal.getType());
    }

    @Test
    public void allShouldWork() throws Exception {
        service.create(animal);
        assertThat(service.all().size()).isEqualTo(1);
    }

    @Test
    public void getShouldWork() throws Exception {
        service.create(animal);
        Animal actual = service.get(animal.getId());
        assertThat(actual).isEqualTo(animal);
        assertThat(actual.getName()).isEqualTo(animal.getName());
        assertThat(actual.getDescription()).isEqualTo(animal.getDescription());
        assertThat(actual.getType()).isEqualTo(animal.getType());
    }
}
