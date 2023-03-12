package cx.catapult.animals.service;

import cx.catapult.animals.domain.BaseAnimal;
import cx.catapult.animals.domain.Group;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
public class AnimalsServiceTest {

    private final AnimalsService service = new AnimalsService();
    private final BaseAnimal ernest = new BaseAnimal("Ernest", "Welsh cob", Group.MAMMALS, "Brown", "Horse");

    @Test
    public void createShouldWork() {
        BaseAnimal thisAnimal = new BaseAnimal("name", "desc", Group.MAMMALS, "Brown", "type");
        BaseAnimal actual = service.create(thisAnimal);
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
        assertThat(service.all().size()).isEqualTo(1);
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
    public void deleteShouldReturnDeletedAnimal() {
        BaseAnimal thisAnimal = new BaseAnimal("id", "name", "desc", Group.MAMMALS, "Brown", "type");
        service.create(thisAnimal);
        assertThat(service.delete(thisAnimal.getId())).isEqualTo(thisAnimal);
    }

    @Test
    public void deleteShouldReturnNullForInvalidAnimaml() {
        BaseAnimal thisAnimal = new BaseAnimal("id", "name", "desc", Group.MAMMALS, "Brown", "type");
        assertThat(service.delete(thisAnimal.getId())).isNull();
    }
}
