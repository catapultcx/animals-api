package cx.catapult.animals.service;

import cx.catapult.animals.domain.Dog;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DogsServiceTest {

    DogsService service = new DogsService();

    @Test
    public void createShouldWork() throws Exception {
        Dog thisDog = new Dog();
        thisDog.setName("Spike");
        thisDog.setDescription("Mouse Dog");

        Dog actual = service.create(thisDog);

        assertThat(actual).isEqualTo(thisDog);
        assertThat(actual.getName()).isEqualTo(thisDog.getName());
        assertThat(actual.getDescription()).isEqualTo(thisDog.getDescription());
        assertThat(actual.getGroup()).isEqualTo(thisDog.getGroup());
    }

    @Test
    public void allShouldWork() throws Exception {
        final Dog dog = new Dog("Spike", "Fluffy dog");

        service.create(dog);

        assertNotNull(dog.getId());
        assertThat(service.all().size()).isEqualTo(1);
    }

    @Test
    public void getShouldWork() throws Exception {
        final Dog dog = new Dog("Spike", "Fluffy dog");

        service.create(dog);

        Dog actual = service.get(dog.getId());

        assertThat(actual).isEqualTo(dog);
        assertThat(actual.getName()).isEqualTo(dog.getName());
        assertThat(actual.getDescription()).isEqualTo(dog.getDescription());
        assertThat(actual.getGroup()).isEqualTo(dog.getGroup());
    }

    @Test
    public void blankIdsShouldBeHandledWhileDeleting() {
        assertThat(service.delete(null)).isEqualTo(Optional.empty());
        assertThat(service.delete("   ")).isEqualTo(Optional.empty());
    }

    @Test
    public void deletingExistingDogShouldWork() {
        final Dog dog = new Dog("Spike", "Fluffy dog");

        Dog created = service.create(dog);

        final Dog deleted = service.delete(created.getId()).get();

        assertThat(deleted.getName()).isEqualTo(dog.getName());
        assertThat(deleted.getDescription()).isEqualTo(dog.getDescription());
    }

    @Test
    public void blankIdsShouldBeHandledWhileUpdating() {
        final Dog dog = new Dog("Spike", "Fluffy dog");

        assertThat(service.update(null, dog)).isEqualTo(Optional.empty());
        assertThat(service.update("   ", dog)).isEqualTo(Optional.empty());
    }

    @Test
    public void nonExistingIdShouldBeHandledWhileUpdating() {
        final Dog dog = new Dog("Spike", "Fluffy dog");

        assertThat(service.update("NonExisting", dog)).isEqualTo(Optional.empty());
    }

    @Test
    public void updatingExistingDogShouldWork() {
        final Dog dog = new Dog("Spike", "Fluffy dog");

        Dog dogToUpdate = service.create(dog);

        dogToUpdate.setName("New name");
        dogToUpdate.setDescription("New description");

        final Dog dogUpdated = service.update(dogToUpdate.getId(), dogToUpdate).get();

        assertThat(dogUpdated.getId()).isEqualTo(dog.getId());
        assertThat(dogUpdated.getName()).isEqualTo(dogToUpdate.getName());
        assertThat(dogUpdated.getDescription()).isEqualTo(dogToUpdate.getDescription());
    }
}
