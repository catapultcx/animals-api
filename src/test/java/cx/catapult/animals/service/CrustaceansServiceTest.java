package cx.catapult.animals.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cx.catapult.animals.domain.Crustacean;
import cx.catapult.animals.exceptions.AnimalNotFoundException;

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
    public void allShouldWork() {
        service.create(crustacean);
        assertThat(service.all().size()).isEqualTo(1);
    }

    @Test
    public void getShouldWork() {
        service.create(crustacean);
        Crustacean actual = service.get(crustacean.getId());
        assertThat(actual).isEqualTo(crustacean);
        assertThat(actual.getName()).isEqualTo(crustacean.getName());
        assertThat(actual.getDescription()).isEqualTo(crustacean.getDescription());
        assertThat(actual.getGroup()).isEqualTo(crustacean.getGroup());
    }

    @Test
    public void deleteShouldWork() throws AnimalNotFoundException {
        Crustacean thisCrustacean = new Crustacean("james", " A crab");
        Crustacean toDelete = service.create(thisCrustacean);
        service.remove(toDelete.getId());
        assertThat(service.all().size()).isEqualTo(0);
    }

    @Test
    public void deleteKeyNotFoundShouldWork() {
        assertThrows(AnimalNotFoundException.class,
                     () -> service.remove("invalid animal"));
    }

    @Test
    public void updateKeyNotFoundShouldWork() {
        assertThrows(AnimalNotFoundException.class,
                     () -> service.update("404", crustacean));
    }
}
