package cx.catapult.animals.service;

import cx.catapult.animals.domain.Arachnid;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AracnidsServiceTest {

    ArachnidsService service = new ArachnidsService();

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

}
