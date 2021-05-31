package cx.catapult.animals.service;

import static org.assertj.core.api.Assertions.assertThat;

import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.domain.Insect;
import org.junit.jupiter.api.Test;

public class InsectsServiceTest {

    InsectsService service = new InsectsService();
    Insect insect = new Insect("Jimminy", "Cricket");

    @Test
    public void createShouldWork() {
        Insect thisInsect = new Insect();
        thisInsect.setName("Jerry");
        thisInsect.setDescription("Mouse Cat");
        Insect actual = service.create(thisInsect);
        assertThat(actual).isEqualTo(thisInsect);
        assertThat(actual.getName()).isEqualTo(thisInsect.getName());
        assertThat(actual.getDescription()).isEqualTo(thisInsect.getDescription());
        assertThat(actual.getGroup()).isEqualTo(thisInsect.getGroup());
    }

    @Test
    public void allShouldWork() {
        service.create(insect);
        assertThat(service.all().size()).isEqualTo(1);
    }

    @Test
    public void getShouldWork() {
        service.create(insect);
        Insect actual = service.get(insect.getId());
        assertThat(actual).isEqualTo(insect);
        assertThat(actual.getName()).isEqualTo(insect.getName());
        assertThat(actual.getDescription()).isEqualTo(insect.getDescription());
        assertThat(actual.getGroup()).isEqualTo(insect.getGroup());
    }
}
