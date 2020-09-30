package cx.catapult.animals.service;

import cx.catapult.animals.domain.Fish;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FishesServiceTest {

    FishesService service = new FishesService();
    Fish fish = new Fish("Tom", "Bob fish");

    @Test
    public void createShouldWork() throws Exception {
        Fish thisFish = new Fish();
        thisFish.setName("Jerry");
        thisFish.setDescription("Mouse Fish");
        Fish actual = service.create(thisFish);
        assertThat(actual).isEqualTo(thisFish);
        assertThat(actual.getName()).isEqualTo(thisFish.getName());
        assertThat(actual.getDescription()).isEqualTo(thisFish.getDescription());
        assertThat(actual.getGroup()).isEqualTo(thisFish.getGroup());
    }

    @Test
    public void allShouldWork() throws Exception {
        service.create(fish);
        assertThat(service.all().size()).isEqualTo(1);
    }

    @Test
    public void getShouldWork() throws Exception {
        service.create(fish);
        Fish actual = service.get(fish.getId());
        assertThat(actual).isEqualTo(fish);
        assertThat(actual.getName()).isEqualTo(fish.getName());
        assertThat(actual.getDescription()).isEqualTo(fish.getDescription());
        assertThat(actual.getGroup()).isEqualTo(fish.getGroup());
    }

    @Test
    public void updateShouldWork() throws Exception {
        service.create(fish);
        fish.setName("Jerry updated");
        fish.setDescription("Mouse Fish update");
        Fish updated = service.update(fish.getId(), fish);
        assertThat(updated).isEqualTo(fish);
        assertThat(updated.getName()).isEqualTo(fish.getName());
        assertThat(updated.getDescription()).isEqualTo(fish.getDescription());
        assertThat(updated.getGroup()).isEqualTo(fish.getGroup());
    }

    @Test
    public void deleteShouldWork() throws Exception {
        service.create(fish);
        service.delete(fish.getId());
        Fish actual = service.get(fish.getId());
        assertThat(actual).isNull();
    }
}
