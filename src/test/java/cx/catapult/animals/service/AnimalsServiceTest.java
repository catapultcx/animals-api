package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;
import cx.catapult.animals.domain.AnimalImpl;
import cx.catapult.animals.domain.Group;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AnimalsServiceTest {

    AnimalsService service = new AnimalsService();
    AnimalImpl animal = new AnimalImpl("Tom", "Bob cat", Group.MAMMALS, "GREY", "CAT");

    @Test
    public void createShouldWork() throws Exception {

        Animal actual = service.create(animal);
        assertThat(actual).isEqualTo(animal);
        assertThat(actual.getName()).isEqualTo(animal.getName());
        assertThat(actual.getDescription()).isEqualTo(animal.getDescription());
        assertThat(actual.getGroup()).isEqualTo(animal.getGroup());
        assertThat(actual.getType()).isEqualTo(animal.getType());
        assertThat(actual.getColor()).isEqualTo(animal.getColor());
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
        assertThat(actual.getGroup()).isEqualTo(animal.getGroup());
		assertThat(actual.getType()).isEqualTo(animal.getType());
		assertThat(actual.getColor()).isEqualTo(animal.getColor());
	}

	@Test
    public void saveShouldWork() throws Exception {
        service.create(animal);
		animal.setColor("BLACK");
        Animal actual = service.save(animal);
        assertThat(actual).isEqualTo(animal);
        assertThat(actual.getName()).isEqualTo(animal.getName());
        assertThat(actual.getDescription()).isEqualTo(animal.getDescription());
        assertThat(actual.getGroup()).isEqualTo(animal.getGroup());
		assertThat(actual.getType()).isEqualTo(animal.getType());
		assertThat("BLACK").isEqualTo(animal.getColor());
	}

	@Test
    public void deleteShouldWork() throws Exception {
        service.create(animal);
		assertThat(service.all().size()).isEqualTo(1);
        service.delete(animal.getId());
		assertThat(service.all().size()).isEqualTo(0);
	}
}
