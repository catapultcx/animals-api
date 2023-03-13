package cx.catapult.animals.service;

import cx.catapult.animals.domain.BaseAnimal;
import cx.catapult.animals.domain.FilterOptions;
import cx.catapult.animals.domain.Group;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AnimalsServiceTest {

    private final AnimalsService service = new AnimalsService();
    private final BaseAnimal ernest = new BaseAnimal("Ernest", "Welsh cob", Group.MAMMALS, "Horse", "Brown");

    @Test
    public void createShouldWork() {
        BaseAnimal thisAnimal = new BaseAnimal("name", "desc", Group.MAMMALS, "type", "Brown");
        BaseAnimal actual = service.create(thisAnimal);
        assertThat(actual.getId()).isNotEmpty();
        assertThat(actual).isEqualTo(thisAnimal);
        assertThat(actual.getName()).isEqualTo(thisAnimal.getName());
        assertThat(actual.getDescription()).isEqualTo(thisAnimal.getDescription());
        assertThat(actual.getGroup()).isEqualTo(thisAnimal.getGroup());
        assertThat(actual.getColour()).isEqualTo(thisAnimal.getColour());
        assertThat(actual.getType()).isEqualTo(thisAnimal.getType());
    }

    @Test
    public void createShouldNotOverwriteExistingId() {
        final String id = "animal 1";
        BaseAnimal thisAnimal = new BaseAnimal(id, "name", "desc", Group.MAMMALS, "type", "Brown");
        BaseAnimal actual = service.create(thisAnimal);

        assertThat(actual.getId()).isEqualTo(id);
        assertThat(actual).isEqualTo(thisAnimal);
        assertThat(actual.getName()).isEqualTo(thisAnimal.getName());
        assertThat(actual.getDescription()).isEqualTo(thisAnimal.getDescription());
        assertThat(actual.getGroup()).isEqualTo(thisAnimal.getGroup());
        assertThat(actual.getColour()).isEqualTo(thisAnimal.getColour());
        assertThat(actual.getType()).isEqualTo(thisAnimal.getType());
    }
    @Test
    public void allShouldWork() {
        service.create(ernest);
        assertThat(service.all(null).size()).isEqualTo(1);
    }

    @Test
    public void allFilteredShouldWork() {
        service.create(ernest);
        BaseAnimal updatedErnest = new BaseAnimal("different name", ernest.getDescription(), ernest.getGroup(), ernest.getType(), ernest.getColour());
        service.create(updatedErnest);
        service.create(new BaseAnimal("another name", "different description", Group.AMPHIBIAN, "type", "orange"));

        FilterOptions filterOptions = new FilterOptions(ernest.getType(), ernest.getName(), ernest.getColour(), ernest.getDescription());
        assertThat(service.all(filterOptions).size()).isEqualTo(1);
    }

    @Test
    public void getShouldWork() {
        service.create(ernest);
        BaseAnimal actual = service.get(ernest.getId());
        assertThat(actual).isEqualTo(ernest);
        assertThat(actual.getName()).isEqualTo(ernest.getName());
        assertThat(actual.getDescription()).isEqualTo(ernest.getDescription());
        assertThat(actual.getGroup()).isEqualTo(ernest.getGroup());
    }

    @Test
    public void updateShouldUpdateAnimalWithId() {
        final String updatedDescription = "updated description";
        service.create(ernest);
        ernest.setDescription(updatedDescription);
        BaseAnimal updated = service.update(ernest.getId(), ernest);
        assertThat(updated).isNotNull();
        assertThat(updated.getDescription()).isEqualTo(updatedDescription);
    }

    @Test
    public void updateShouldReturnNullForInvalidId() {
        service.create(ernest);
        BaseAnimal updated = service.update("invalid id", ernest);
        assertThat(updated).isNull();
    }

    @Test
    public void deleteShouldReturnDeletedAnimal() {
        BaseAnimal thisAnimal = new BaseAnimal("id", "name", "desc", Group.MAMMALS, "type", "Brown");
        service.create(thisAnimal);
        assertThat(service.delete(thisAnimal.getId())).isEqualTo(thisAnimal);
    }

    @Test
    public void deleteShouldReturnNullForInvalidAnimalId() {
        BaseAnimal thisAnimal = new BaseAnimal("id", "name", "desc", Group.MAMMALS, "type", "Brown");
        assertThat(service.delete(thisAnimal.getId())).isNull();
    }
}
