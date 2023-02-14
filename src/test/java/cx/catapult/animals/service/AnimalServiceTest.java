package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AnimalServiceTest {

    AnimalService service = new AnimalService(Animal.AnimalType.DOG);
    Animal animal = new Animal(Animal.AnimalType.DOG, "Pluto", "Disney");

    @Test
    public void createShouldWork() {
        Animal animal = new Animal();
        animal.setType(Animal.AnimalType.DOG);
        animal.setName("Zeytin");
        animal.setDescription("My buddy");
        Animal actual = service.create(animal);
        assertThat(actual).isEqualTo(animal);
        assertThat(actual.getName()).isEqualTo(animal.getName());
        assertThat(actual.getDescription()).isEqualTo(animal.getDescription());
        assertThat(actual.getGroup()).isEqualTo(animal.getGroup());
    }

    @Test
    public void allShouldWork() {
        service.create(animal);
        assertThat(service.all().size()).isEqualTo(1);
    }

    @Test
    public void getShouldWork() {
        service.create(animal);
        Animal actual = service.get(animal.getId());
        assertThat(actual).isEqualTo(animal);
        assertThat(actual.getName()).isEqualTo(animal.getName());
        assertThat(actual.getDescription()).isEqualTo(animal.getDescription());
        assertThat(actual.getGroup()).isEqualTo(animal.getGroup());
    }
}
