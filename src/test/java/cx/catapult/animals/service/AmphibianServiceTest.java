package cx.catapult.animals.service;

import static org.assertj.core.api.Assertions.assertThat;

import cx.catapult.animals.domain.BaseAmphibian;
import cx.catapult.animals.domain.BaseAnimal;
import org.junit.jupiter.api.Test;

public class AmphibianServiceTest {

    private final AmphibianService underTest = new AmphibianService();

    @Test
    public void shouldStoreAmphibianInformation() {
        final BaseAmphibian testAmphibian = new BaseAmphibian("testAmphib", "This amphibian is a test");
        final BaseAmphibian actual = underTest.create(testAmphibian);
        assertThat(actual).isEqualTo(testAmphibian);
        assertThat(actual.getName()).isEqualTo(testAmphibian.getName());
        assertThat(actual.getDescription()).isEqualTo(testAmphibian.getDescription());
        assertThat(actual.getGroup()).isEqualTo(testAmphibian.getGroup());
    }
}
