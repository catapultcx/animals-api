package cx.catapult.animals.service;

import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.domain.Horse;
import cx.catapult.animals.exception.AnimalNotFoundException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HorseServiceTest {

    HorseService service = new HorseService();
    Horse horse = new Horse("Spirit", "Black Stallion");

    @Test
    void createShouldWork() {
        Horse actual = service.create(horse);
        assertThat(actual).isEqualTo(horse);
        assertThat(actual.getName()).isEqualTo(horse.getName());
        assertThat(actual.getDescription()).isEqualTo(horse.getDescription());
        assertThat(actual.getGroup()).isEqualTo(horse.getGroup());
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
    public void delete_shouldThrowException() {
        Throwable exception = assertThrows(AnimalNotFoundException.class, () -> service.delete("1"));
        assertThat(exception.getMessage()).isEqualTo("Record not found");
    }
}
