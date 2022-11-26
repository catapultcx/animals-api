package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;
import cx.catapult.animals.domain.AnimalFactory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AnimalServiceTest {

    private final AnimalService service = new AnimalService();
    private final Animal animal = AnimalFactory.aCat() ;

    @Test
    public void createShouldWork() throws Exception {
        var actual = service.create(animal);
        assertThat(actual).isEqualTo(animal);
        assertThat(actual.getName()).isEqualTo(animal.getName());
        assertThat(actual.getDescription()).isEqualTo(animal.getDescription());
        assertThat(actual.getType()).isEqualTo(animal.getType());
    }

    @Test
    public void allShouldWork() throws Exception {
        service.create(animal);
        assertThat(service.all().size()).isEqualTo(1);
    }

    @Test
    public void getShouldWork() throws Exception {
        service.create(animal);
        var actual = service.get(animal.getId());
        assertThat(actual).isEqualTo(animal);
        assertThat(actual.getName()).isEqualTo(animal.getName());
        assertThat(actual.getDescription()).isEqualTo(animal.getDescription());
        assertThat(actual.getType()).isEqualTo(animal.getType());
        assertThat(actual.getColour()).isEqualTo(animal.getColour());
    }
}
