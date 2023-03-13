package cx.catapult.animals.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;
import org.junit.jupiter.api.Test;
import cx.catapult.animals.domain.Animal;
import cx.catapult.animals.domain.AnimalType;
import cx.catapult.animals.exception.AnimalServiceException;

public class AnimalServiceTest {

    AnimalService service = new AnimalService();
    Animal animal = new Animal("Tom", "Bob cat", "grey", "mammals");

    @Test
    public void createShouldWork() {
        Animal thisAnimal = new Animal();
        thisAnimal.setName("Jerry");
        thisAnimal.setDescription("Mouse Cat");
        thisAnimal.setColour("grey");
        thisAnimal.setType(AnimalType.get("amphibian"));
        Animal actual = service.create(thisAnimal);
        assertThat(actual).isEqualTo(thisAnimal);
        assertThat(actual.getName()).isEqualTo(thisAnimal.getName());
        assertThat(actual.getDescription()).isEqualTo(thisAnimal.getDescription());
        assertThat(actual.getColour()).isEqualTo(thisAnimal.getColour());
        assertThat(actual.getType()).isEqualTo(thisAnimal.getType());
    }

    @Test
    public void allShouldWork() {
        service.create(animal);
        assertThat(service.filter("", "", "", "").size()).isEqualTo(1);
    }

    @Test
    public void getShouldWork() {
        service.create(animal);
        Animal actual = service.get(animal.getId());
        assertThat(actual).isEqualTo(animal);
        assertThat(actual.getName()).isEqualTo(animal.getName());
        assertThat(actual.getDescription()).isEqualTo(animal.getDescription());
        assertThat(actual.getColour()).isEqualTo(animal.getColour());
        assertThat(actual.getType()).isEqualTo(animal.getType());
    }

    @Test
    public void getShouldFail() {
        Exception exception = assertThrows(AnimalServiceException.class, () -> {
            service.create(animal);
            service.get(animal.getId() + "-invalid");
        });
        assertTrue(exception.getMessage().contains(String.format("Animal not found for id: %s", animal.getId() + "-invalid")));
    }

    @Test
    public void updateShouldWork() {
        service.create(animal);
        animal.setName("Jerry updated");
        animal.setDescription("Mouse Cat updated");
        animal.setColour("black");
        animal.setType(AnimalType.get("invertebrate"));
        Animal actual = service.update(animal);
        assertThat(actual).isEqualTo(animal);
        assertThat(actual.getName()).isEqualTo(animal.getName());
        assertThat(actual.getDescription()).isEqualTo(animal.getDescription());
        assertThat(actual.getColour()).isEqualTo(animal.getColour());
        assertThat(actual.getType()).isEqualTo(animal.getType());
    }

    @Test
    public void updateShouldFail() {
        service.create(animal);
        animal.setId("invalid-id");
        animal.setName("Jerry updated");
        animal.setDescription("Mouse Cat updated");
        animal.setColour("black");
        animal.setType(AnimalType.get("invertebrate"));

        Exception exception = assertThrows(AnimalServiceException.class, () -> service.update(animal));
        assertTrue(exception.getMessage().contains(String.format("Animal not found for id: %s", "invalid-id")));
    }

    @Test
    public void deleteShouldWork() {
        service.create(animal);
        assertNotNull(animal.getId());

        service.delete(animal.getId());
        assertThat(service.filter("", "", "", "").size()).isEqualTo(0);
    }

    @Test
    public void deleteShouldFail() {
        service.create(animal);
        assertNotNull(animal.getId());

        Exception exception = assertThrows(AnimalServiceException.class, () -> service.delete("invalid-id"));
        assertTrue(exception.getMessage().contains("Animal not found for id: invalid-id"));
    }

    @Test
    public void filterShouldReturnEmpty() {
        service.create(new Animal("tom", "lorem", "blue", "amphibian"));
        service.create(new Animal("jerry", "ipsum", "grey", "bird"));
        service.create(new Animal("lion", "test desc", "orange", "mammals"));

        Collection<Animal> results = service.filter("tom2", "", "", "");
        assertThat(results.size()).isEqualTo(0);
    }

    @Test
    public void filterShouldWork() {
        service.create(new Animal("tom", "lorem", "blue", "amphibian"));
        service.create(new Animal("jerry", "ipsum", "grey", "bird"));
        service.create(new Animal("lion", "test desc", "orange", "mammals"));
        service.create(new Animal("rabbit", "desc", "red", "invertebrate"));

        Collection<Animal> resultsByName = service.filter("tom", "", "", "");
        assertThat(resultsByName.size()).isEqualTo(1);

        Collection<Animal> resultsByDescription = service.filter("", "ipsum", "", "");
        assertThat(resultsByDescription.size()).isEqualTo(1);

        Collection<Animal> resultsByColour = service.filter("", "", "orange", "");
        assertThat(resultsByColour.size()).isEqualTo(1);

        Collection<Animal> resultsByType = service.filter("", "", "", "invertebrate");
        assertThat(resultsByType.size()).isEqualTo(1);
    }
}


