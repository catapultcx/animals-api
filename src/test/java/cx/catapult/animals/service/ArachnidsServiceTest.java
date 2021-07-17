package cx.catapult.animals.service;

import static org.assertj.core.api.Assertions.assertThat;

import cx.catapult.animals.domain.Arachnid;
import cx.catapult.animals.domain.Cat;
import org.junit.jupiter.api.Test;

public class ArachnidsServiceTest {

    private ArachnidsService service = new ArachnidsService();
    private Arachnid arachnid = new Arachnid("Spider McSpiderface", "Hairy");

    @Test
    public void createShouldWork() throws Exception {
        Arachnid thisArachnid = new Arachnid();
        thisArachnid.setName("Simon");
        thisArachnid.setDescription("House spider");
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
