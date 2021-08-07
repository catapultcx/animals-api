package cx.catapult.animals.service;

import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.domain.Horse;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class HorsesServiceTest {

    HorsesService service = new HorsesService();
    Horse horse = new Horse("Black Beauty", "its Beauty");

    @Test
    public void createShouldWork() throws Exception {
        Horse thisHorse = new Horse();
        thisHorse.setName("Black Beauty");
        thisHorse.setDescription("its Beauty");
        Horse actual = service.create(thisHorse);
        assertThat(actual).isEqualTo(thisHorse);
        assertThat(actual.getName()).isEqualTo(thisHorse.getName());
        assertThat(actual.getDescription()).isEqualTo(thisHorse.getDescription());
        assertThat(actual.getGroup()).isEqualTo(thisHorse.getGroup());
    }

    @Test
    public void allShouldWork() throws Exception {
        service.create(horse);
        assertThat(service.all().size()).isEqualTo(1);
    }

    @Test
    public void getShouldWork() throws Exception {
        service.create(horse);
        Horse actual = service.get(horse.getId());
        assertThat(actual).isEqualTo(horse);
        assertThat(actual.getName()).isEqualTo(horse.getName());
        assertThat(actual.getDescription()).isEqualTo(horse.getDescription());
        assertThat(actual.getGroup()).isEqualTo(horse.getGroup());
    }

    @Test
    public void shouldRemoveHorse() throws Exception {
        service.create(horse);
        boolean isDeleted = service.delete(horse.getId());
        assertThat(isDeleted).isTrue();
        assertThat(service.all().size()).isEqualTo(0);
    }

    @Test
    public void shouldReturnsFalseIfNotFound() throws Exception {
        service.create(horse);
        boolean isDeleted = service.delete(UUID.randomUUID().toString());
        assertThat(isDeleted).isFalse();
        assertThat(service.all().size()).isEqualTo(1);
    }
}
