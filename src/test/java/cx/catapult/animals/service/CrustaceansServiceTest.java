package cx.catapult.animals.service;

import static org.assertj.core.api.Assertions.assertThat;

import cx.catapult.animals.domain.Crustacean;
import org.junit.jupiter.api.Test;

public class CrustaceansServiceTest {
    private CrustaceansService service = new CrustaceansService();
    private Crustacean crustacean = new Crustacean("James", "A crab");

    @Test
    public void createShouldWork() throws Exception {
        Crustacean thisCrustacean = new Crustacean("james", " A crab");
        Crustacean actual = service.create(thisCrustacean);
        assertThat(actual).isEqualTo(thisCrustacean);
        assertThat(actual.getName()).isEqualTo(thisCrustacean.getName());
        assertThat(actual.getDescription()).isEqualTo(thisCrustacean.getDescription());
        assertThat(actual.getGroup()).isEqualTo(thisCrustacean.getGroup());
    }

    @Test
    public void allShouldWork() throws Exception {
        service.create(crustacean);
        assertThat(service.all().size()).isEqualTo(1);
    }

    @Test
    public void getShouldWork() throws Exception {
        service.create(crustacean);
        Crustacean actual = service.get(crustacean.getId());
        assertThat(actual).isEqualTo(crustacean);
        assertThat(actual.getName()).isEqualTo(crustacean.getName());
        assertThat(actual.getDescription()).isEqualTo(crustacean.getDescription());
        assertThat(actual.getGroup()).isEqualTo(crustacean.getGroup());
    }
}
