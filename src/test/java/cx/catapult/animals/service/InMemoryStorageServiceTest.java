package cx.catapult.animals.service;

import static org.assertj.core.api.Assertions.assertThat;

import cx.catapult.animals.domain.BaseAnimal;
import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.domain.Group;
import java.util.Collection;
import org.junit.jupiter.api.Test;

public class InMemoryStorageServiceTest {
    private final InMemoryStorageService<BaseAnimal> underTest = new InMemoryStorageService<BaseAnimal>();

    @Test
    public void shouldCreate() {
        final BaseAnimal baseAnimal = underTest.create(new BaseAnimal("name", "description", Group.BIRD));
        assertThat(baseAnimal).isNotNull();
    }

    @Test
    public void shouldReturnAll() {
        underTest.create(new BaseAnimal("name", "description", Group.BIRD));
        final Collection<BaseAnimal> all = underTest.all();
        assertThat(all).hasSize(1);
    }

    @Test
    public void shouldGet() {
        final BaseAnimal baseAnimal = underTest.create(new BaseAnimal("name", "description", Group.BIRD));
        final BaseAnimal returnedAnimal = underTest.get(baseAnimal.getId());
        assertThat(baseAnimal).isEqualTo(returnedAnimal);
    }

    @Test
    public void shouldDelete() {
        final BaseAnimal baseAnimal = underTest.create(new BaseAnimal("name", "description", Group.BIRD));
        underTest.delete(baseAnimal.getId());
        final Collection<BaseAnimal> all = underTest.all();
        assertThat(all).isEmpty();
    }

    @Test
    public void shouldUpdate() {
        final BaseAnimal baseAnimal = underTest.create(new BaseAnimal("name", "description", Group.BIRD));
        final BaseAnimal updatedAnimal = underTest.update(baseAnimal.getId(), new Cat("eman", "noitpircsed"));
        final Collection<BaseAnimal> all = underTest.all();
        assertThat(all).contains(updatedAnimal)
                       .doesNotContain(baseAnimal);
    }
}
