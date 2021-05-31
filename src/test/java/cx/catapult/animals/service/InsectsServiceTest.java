package cx.catapult.animals.service;

import static org.assertj.core.api.Assertions.assertThat;

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

    @Test
    public void deleteShouldWork() {
        int sizeBefore = service.all().size();
        service.create(insect);
        assertThat(service.all().size()).isEqualTo(sizeBefore + 1);
        service.delete(insect.getId());
        assertThat(service.all().size()).isEqualTo(sizeBefore);
    }


    @Test
    public void updateShouldWork() {
        Insect initialInsect = service.create(insect);
        Insect replacement = new Insect("Nota", "Grasshopper");
        Insect finalInsect = service.update(initialInsect.getId(), replacement);
        assertThat(finalInsect.getDescription()).isEqualTo(replacement.getDescription());
        assertThat(finalInsect.getName()).isEqualTo(replacement.getName());
    }

}
