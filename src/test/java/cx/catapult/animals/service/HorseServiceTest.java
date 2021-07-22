package cx.catapult.animals.service;

import cx.catapult.animals.domain.Horse;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HorseServiceTest {

    HorseService service = new HorseService();

    @Test
    void createShouldWork() {
        Horse thisHorse = new Horse();
        thisHorse.setName("Jerry");
        thisHorse.setDescription("Mouse Horse");
        Horse actual = service.create(thisHorse);
        assertThat(actual).isEqualTo(thisHorse);
        assertThat(actual.getName()).isEqualTo(thisHorse.getName());
        assertThat(actual.getDescription()).isEqualTo(thisHorse.getDescription());
        assertThat(actual.getGroup()).isEqualTo(thisHorse.getGroup());
    }
}
