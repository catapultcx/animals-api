package cx.catapult.animals.service;

import cx.catapult.animals.domain.Animal;
import cx.catapult.animals.domain.AnimalFactory;
import cx.catapult.animals.domain.AnimalType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AnimalServiceTest {

    private final AnimalService service = new AnimalService();
    private final Animal aCat = AnimalFactory.aCat() ;

    @Test
    public void createShouldWork() throws Exception {
        var actual = service.create(aCat);
        assertThat(actual).isEqualTo(aCat);
        assertThat(actual.getName()).isEqualTo(aCat.getName());
        assertThat(actual.getDescription()).isEqualTo(aCat.getDescription());
        assertThat(actual.getType()).isEqualTo(aCat.getType());
    }

    @Test
    public void allShouldWork() throws Exception {
        service.create(aCat);
        assertThat(service.all().size()).isEqualTo(1);
    }

    @Test
    public void getShouldWork() throws Exception {
        service.create(aCat);
        var actual = service.get(aCat.getId());
        assertThat(actual).isEqualTo(aCat);
        assertThat(actual.getName()).isEqualTo(aCat.getName());
        assertThat(actual.getDescription()).isEqualTo(aCat.getDescription());
        assertThat(actual.getType()).isEqualTo(aCat.getType());
        assertThat(actual.getColour()).isEqualTo(aCat.getColour());
    }

    @Test
    public void deleteShouldWorkIfAnimialWithIdExist() {
        service.create(aCat);
        var id = aCat.getId();

        var deleted = service.delete(id);

        assertThat(deleted.getId()).isEqualTo(id);
        assertNull(service.get(id));
    }

    @Test
    public void updateShouldWorkIfAnimialWithIdExist() {
        service.create(aCat);
        var id = aCat.getId();

        var animalToUpdate = new Animal("updated", "updated desc", "Red", AnimalType.MAMMALS);

        var updated = service.update(id, animalToUpdate);

        assertThat(updated.getId()).isEqualTo(id);
        assertThat(updated.getName()).isEqualTo(animalToUpdate.getName());
        assertThat(updated.getDescription()).isEqualTo(animalToUpdate.getDescription());
        assertThat(updated.getColour()).isEqualTo(animalToUpdate.getColour());
    }
}
