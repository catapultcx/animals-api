package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;
import cx.catapult.animals.domain.Type;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AnimalServiceTest {

    AnimalService service = new AnimalService();
    Animal animal = new Animal("Tom", "Bob cat", "grey", "mammals");

    @Test
    public void createShouldWork() throws Exception {
        Animal thisAnimal = new Animal();
        thisAnimal.setName("Jerry");
        thisAnimal.setDescription("Mouse Cat");
        thisAnimal.setColour("grey");
        thisAnimal.setType(Type.get("amphibian"));
        Animal actual = service.create(thisAnimal);
        assertThat(actual).isEqualTo(thisAnimal);
        assertThat(actual.getName()).isEqualTo(thisAnimal.getName());
        assertThat(actual.getDescription()).isEqualTo(thisAnimal.getDescription());
        assertThat(actual.getColour()).isEqualTo(thisAnimal.getColour());
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
        assertThat(actual.getColour()).isEqualTo(animal.getColour());
        assertThat(actual.getType()).isEqualTo(animal.getType());
    }

    @Test
    public void updateShouldWork() throws Exception {
        service.create(animal);
        animal.setName("Jerry updated");
        animal.setDescription("Mouse Cat updated");
        animal.setColour("black");
        animal.setType(Type.get("invertebrate"));
        Animal actual = service.update(animal);
        assertThat(actual).isEqualTo(animal);
        assertThat(actual.getName()).isEqualTo(animal.getName());
        assertThat(actual.getDescription()).isEqualTo(animal.getDescription());
        assertThat(actual.getColour()).isEqualTo(animal.getColour());
        assertThat(actual.getType()).isEqualTo(animal.getType());
    }

    @Test
    public void deleteShouldWork() throws Exception {
        service.create(animal);
        assertNotNull(animal.getId());

        service.delete(animal.getId());
        assertThat(service.all().size()).isEqualTo(0);
    }
}
