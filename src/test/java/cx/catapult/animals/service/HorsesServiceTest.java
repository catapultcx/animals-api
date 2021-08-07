package cx.catapult.animals.service;

import cx.catapult.animals.domain.Horse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class HorsesServiceTest {

    @Autowired
    HorsesService service;

    Horse horse = new Horse("Black Beauty", "its Beauty");

    @Test
    public void createShouldWork() throws Exception {
        Horse thisHorse = new Horse();
        thisHorse.setName("Black Beauty");
        thisHorse.setDescription("its Beauty");
        Horse actual = service.create(thisHorse);
        assertThat(actual.getName()).isEqualTo(thisHorse.getName());
        assertThat(actual.getDescription()).isEqualTo(thisHorse.getDescription());
        assertThat(actual.getGroup()).isEqualTo(thisHorse.getGroup());
    }

    @Test
    public void allShouldWork() throws Exception {
        int size = service.all().size();
        service.create(horse);
        assertThat(service.all().size()).isEqualTo(size+1);
    }

    @Test
    public void getShouldWork() throws Exception {
        Horse horse = service.create(this.horse);
        Horse actual = service.get(horse.getId());
        assertThat(actual.getId()).isEqualTo(horse.getId());
        assertThat(actual.getName()).isEqualTo(horse.getName());
        assertThat(actual.getDescription()).isEqualTo(horse.getDescription());
        assertThat(actual.getGroup()).isEqualTo(horse.getGroup());
    }

    @Test
    public void shouldRemoveHorse() throws Exception {
        Horse horse = service.create(this.horse);
        boolean isDeleted = service.delete(horse.getId());
        assertThat(isDeleted).isTrue();
    }

    @Test
    public void shouldReturnsFalseIfNotFound() throws Exception {
        service.create(horse);
        boolean isDeleted = service.delete(UUID.randomUUID().toString());
        assertThat(isDeleted).isFalse();
    }

    @Test
    public void shouldUpdate() throws Exception {
        Horse horse = service.create(this.horse);
        int sizeBeforeUpdate = service.all().size();
        service.update(horse.getId(), horse);
        int sizeAfterUpdate = service.all().size();
        assertThat(sizeBeforeUpdate).isEqualTo(sizeAfterUpdate);
    }

}
