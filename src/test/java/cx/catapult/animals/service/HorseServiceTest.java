package cx.catapult.animals.service;

import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.domain.Horse;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HorseServiceTest {

    HorseService service = new HorseService();
    Horse horse = new Horse("Robin", "Breed from UK");

    @Test
    public void shouldCreateHorse() throws Exception {
        Horse givenHorse = new Horse();
        givenHorse.setName("Robin");
        givenHorse.setDescription("Breed from UK");
        Horse actualHorse = service.create(givenHorse);
        assertThat(actualHorse).isEqualTo(givenHorse);
    }

    @Test
    public void shouldReturnAllHorses() throws Exception {
        service.create(horse);
        assertThat(service.all().size()).isEqualTo(1);
    }

    @Test
    public void getHorseShouldWork() throws Exception {
        service.create(horse);
        Horse actual = service.get(horse.getId());
        assertThat(actual).isEqualTo(horse);
        assertThat(actual.getName()).isEqualTo(horse.getName());
        assertThat(actual.getDescription()).isEqualTo(horse.getDescription());
        assertThat(actual.getGroup()).isEqualTo(horse.getGroup());
    }
}
