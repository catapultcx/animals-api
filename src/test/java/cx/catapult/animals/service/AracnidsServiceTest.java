package cx.catapult.animals.service;

import cx.catapult.animals.domain.Arachnid;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AracnidsServiceTest {

    ArachnidsService service = new ArachnidsService();

    Arachnid arachnid = new Arachnid("spider", "spiderman");

    @Test
    public void createShouldWork() {
        Arachnid thisArachnid = new Arachnid();
        thisArachnid.setName("testArachnid");
        thisArachnid.setDescription("test arachnid");
        Arachnid actual = service.create(thisArachnid);
        assertThat(actual).isEqualTo(thisArachnid);
        assertThat(actual.getName()).isEqualTo(thisArachnid.getName());
        assertThat(actual.getDescription()).isEqualTo(thisArachnid.getDescription());
        assertThat(actual.getGroup()).isEqualTo(thisArachnid.getGroup());
    }

    @Test
    public void allShouldWork() throws Exception {
        service.create(arachnid);
        assertThat(service.all().size()).isEqualTo(1);
    }

    @Test
    public void getShouldWork() throws Exception {
        service.create(arachnid);
        Arachnid actual = service.get(arachnid.getId());
        assertThat(actual).isEqualTo(arachnid);
        assertThat(actual.getName()).isEqualTo(arachnid.getName());
        assertThat(actual.getDescription()).isEqualTo(arachnid.getDescription());
        assertThat(actual.getGroup()).isEqualTo(arachnid.getGroup());
    }

}
