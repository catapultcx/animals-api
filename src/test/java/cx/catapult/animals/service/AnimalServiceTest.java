package cx.catapult.animals.service;

import cx.catapult.animals.domain.BaseAnimal;
import cx.catapult.animals.domain.Type;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class AnimalServiceTest {
    private AnimalService animalService = new AnimalService();
    private final BaseAnimal animal = new BaseAnimal("Snoopy", "a cute dog", "brown", Type.MAMMALS);

    @Test
    public void testCreateAnimal() {
        BaseAnimal created = animalService.create(animal);
        assertThat(created.getId()).isNotNull();
        assertThat(created.getName()).isEqualTo("Snoopy");
        assertThat(created.getType()).isEqualTo(Type.MAMMALS);
        assertThat(created.getColor()).isEqualTo("brown");
        assertThat(created.getDescription()).isEqualTo("a cute dog");
    }

    @Test
    public void testGetAnimal() {
        BaseAnimal created = animalService.create(animal);
        BaseAnimal baseAnimal = animalService.get(created.getId());
        assertThat(baseAnimal.getName()).isEqualTo("Snoopy");
        assertThat(baseAnimal.getType()).isEqualTo(Type.MAMMALS);
        assertThat(baseAnimal.getColor()).isEqualTo("brown");
        assertThat(baseAnimal.getDescription()).isEqualTo("a cute dog");
    }

    @Test
    public void testDeleteAnimal() {
        BaseAnimal created = animalService.create(animal);
        animalService.delete(created.getId());
        BaseAnimal baseAnimal = animalService.get(created.getId());
        assertThat(baseAnimal).isNull();
    }

    @Test
    public void testUpdateAnimal() {
        BaseAnimal created = animalService.create(animal);
        BaseAnimal update = new BaseAnimal();
        update.setName("Snuppy");
        update.setDescription("a growing pup");
        update.setColor(created.getColor());
        update.setType(created.getType());
        BaseAnimal updated = animalService.update(created.getId(), update);
        assertThat(updated.getName()).isEqualTo("Snuppy");
        assertThat(updated.getDescription()).isEqualTo("a growing pup");
    }

    @Test
    public void testDeleteAllAnimals() {
        animalService.create(animal);
        animalService.create(animal);
        animalService.deleteAll();
        assertThat(animalService.all().size()).isEqualTo(0);
    }

    @Test
    public void testGetAll() {
        animalService.create(animal);
        animalService.create(animal);
        assertThat(animalService.all().size()).isEqualTo(2);
    }

    @Test
    public void testGetAnimalStore() {
        animalService.create(animal);
        animalService.create(animal);
        HashMap<String, BaseAnimal> animalStore = animalService.getAnimalStore();
        assertThat(animalStore.size()).isEqualTo(2);
    }

}
