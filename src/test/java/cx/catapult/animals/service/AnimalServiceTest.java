package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;
import cx.catapult.animals.domain.Type;
import cx.catapult.animals.exception.OperationNotAllowedException;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
        assertThat(service.filter("", "", "", "").size()).isEqualTo(1);
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
    public void getShouldFail() throws Exception {
        Exception exception = assertThrows(OperationNotAllowedException.class, () -> {
            service.create(animal);
            service.get(animal.getId()+"-invalid");
        });
        assertTrue(exception.getMessage().contains(String.format("Animal not found for id: %s", animal.getId()+"-invalid")));
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
    public void updateShouldFail() throws Exception {
        service.create(animal);
        animal.setId("invalid-id");
        animal.setName("Jerry updated");
        animal.setDescription("Mouse Cat updated");
        animal.setColour("black");
        animal.setType(Type.get("invertebrate"));

        Exception exception = assertThrows(OperationNotAllowedException.class, () -> {
            service.update(animal);
        });
        assertTrue(exception.getMessage().contains(String.format("Animal not found for id: %s", "invalid-id")));
    }

    @Test
    public void deleteShouldWork() throws Exception {
        service.create(animal);
        assertNotNull(animal.getId());

        service.delete(animal.getId());
        assertThat(service.filter("", "", "", "").size()).isEqualTo(0);
    }

    @Test
    public void deleteShouldFail() throws Exception {
        service.create(animal);
        assertNotNull(animal.getId());

        Exception exception = assertThrows(OperationNotAllowedException.class, () -> {
            service.delete("invalid-id");
        });
        assertTrue(exception.getMessage().contains("Animal not found for id: invalid-id"));
    }

    @Test
    public void filterShouldReturnEmpty() throws Exception {
        service.create(new Animal("tom", "lorem", "blue", "amphibian"));
        service.create(new Animal("jerry", "ipsum", "grey", "bird"));
        service.create(new Animal("lion", "test desc", "orange", "mammals"));

        Collection<Animal> results = service.filter("tom2", "", "", "");
        assertThat(results.size()).isEqualTo(0);
    }

    @Test
    public void filterShouldWork() throws Exception {
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
