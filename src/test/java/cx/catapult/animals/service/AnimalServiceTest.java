package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;
import cx.catapult.animals.domain.BaseAnimal;
import cx.catapult.animals.domain.Type;
import cx.catapult.animals.web.AnimalFilter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.stream.Stream;

import static org.apache.commons.lang3.RandomUtils.nextInt;
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


    @ParameterizedTest
    @MethodSource("provideOptionsForAnimalFilter")
    public void testAnimalFilter(AnimalFilter filter, int expected) {
        final String animalName = "Trace";
        create(animalName, "brown", Type.AMPHIBIAN);
        create(animalName + nextInt(), "brown", Type.MAMMALS);
        create(animalName + nextInt(), "white", Type.MAMMALS);
        create(animalName + nextInt(), "red",   Type.REPTILES);
        create(animalName + nextInt(), "green", Type.INVERTEBRATE);
        create(animalName + nextInt(), "grey",  Type.FISH);
        create(animalName + nextInt(), "blue",  Type.FISH);
        create(animalName + nextInt(), "white", Type.BIRD);

        assertThat(animalService.search(filter).size()).isEqualTo(expected);
    }

    private static Stream<Arguments> provideOptionsForAnimalFilter() {
        return Stream.of(
                Arguments.of(new AnimalFilter(null, null, "white", null), 2),
                Arguments.of(new AnimalFilter(null, "MAMMALS", "white", null), 1),
                Arguments.of(new AnimalFilter("Trace", null, null, null), 1),
                Arguments.of(new AnimalFilter(null, null, null, "Trace"), 1),
                Arguments.of(new AnimalFilter(null, "FISH", null, null), 2)
        );
    }

    private Animal create(String name, String color, Type type) {
        BaseAnimal animal = new BaseAnimal(name, name, color, type);
        return animalService.create(animal);
    }

}
