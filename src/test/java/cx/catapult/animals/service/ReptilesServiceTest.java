package cx.catapult.animals.service;

import cx.catapult.animals.domain.Reptile;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ReptilesServiceTest {


    ReptilesService service = new ReptilesService();
    Reptile reptile = new Reptile("Sam", "Sam snake");

    @Test
    public void createShouldWork() throws Exception {
        Reptile thisReptile = new Reptile();
        thisReptile.setName("Leo");
        thisReptile.setDescription("Leo Lizard");
        Reptile actual = service.create(thisReptile);
        assertThat(actual).isEqualTo(thisReptile);
        assertThat(actual.getName()).isEqualTo(thisReptile.getName());
        assertThat(actual.getDescription()).isEqualTo(thisReptile.getDescription());
        assertThat(actual.getGroup()).isEqualTo(thisReptile.getGroup());
    }

    @Test
    public void allShouldWork() throws Exception {
        service.create(reptile);
        assertThat(service.all().size()).isEqualTo(1);
    }

    @Test
    public void getShouldWork() throws Exception {
        service.create(reptile);
        Reptile actual = service.get(reptile.getId());
        assertThat(actual).isEqualTo(reptile);
        assertThat(actual.getName()).isEqualTo(reptile.getName());
        assertThat(actual.getDescription()).isEqualTo(reptile.getDescription());
        assertThat(actual.getGroup()).isEqualTo(reptile.getGroup());
    }
}
