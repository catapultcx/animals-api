package cx.catapult.animals.service;

import cx.catapult.animals.domain.BaseAnimal;
import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.domain.Group;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AnimalServiceTest {

    AnimalService service = new AnimalService();
    BaseAnimal cat = new Cat("Tom", "Bob cat");
    BaseAnimal shark = new BaseAnimal("Bruce", "Finding Nemo", Group.FISH);

    @Test
    public void createShouldWork() throws Exception {
        Cat thisCat = new Cat();
        thisCat.setName("Jerry");
        thisCat.setDescription("Mouse Cat");
        BaseAnimal actual = service.create(thisCat);
        assertThat(actual).isEqualTo(thisCat);
        assertThat(actual.getName()).isEqualTo(thisCat.getName());
        assertThat(actual.getDescription()).isEqualTo(thisCat.getDescription());
        assertThat(actual.getGroup()).isEqualTo(thisCat.getGroup());
    }

    @Test
    public void allShouldWork() throws Exception {
        service.create(cat);
        assertThat(service.all().size()).isEqualTo(1);
    }

    @Test
    public void getShouldWork() throws Exception {
        service.create(cat);
        BaseAnimal actual = service.get(cat.getId());
        assertThat(actual).isEqualTo(cat);
        assertThat(actual.getName()).isEqualTo(cat.getName());
        assertThat(actual.getDescription()).isEqualTo(cat.getDescription());
        assertThat(actual.getGroup()).isEqualTo(cat.getGroup());
    }

    @Test
    public void deleteShouldDeleteAnimalIfIdExists() throws Exception {
        service.create(shark);
        service.create(cat);

        Collection<BaseAnimal> allAnimals = service.all();

        assertThat(allAnimals).hasSize(2);
        service.delete(cat.getId());

        Collection<BaseAnimal> fishes = service.all();
        assertThat(fishes).hasSize(1);
        assertThat(fishes.contains(shark)).isTrue();
    }

    @Test
    public void deleteShouldOnlyOnce() throws Exception {
        service.create(shark);
        service.create(cat);

        Collection<BaseAnimal> allAnimals = service.all();

        assertThat(allAnimals).hasSize(2);
        service.delete(cat.getId());
        service.delete(cat.getId());

        Collection<BaseAnimal> fishes = service.all();
        assertThat(fishes).hasSize(1);
        assertThat(fishes.contains(shark)).isTrue();
    }
}
