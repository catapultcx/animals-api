package cx.catapult.animals.service;

import static org.assertj.core.api.Assertions.assertThat;

import cx.catapult.animals.domain.BaseAmphibian;
import cx.catapult.animals.domain.Cat;
import org.junit.jupiter.api.Test;

public class AmphibianServiceTest {

    private final AmphibianService underTest = new AmphibianService();
    private final BaseAmphibian amphibian = new BaseAmphibian("Tom", "Bob cat");

    @Test
    public void shouldStoreAmphibianInformation() {
        final BaseAmphibian testAmphibian = new BaseAmphibian("testAmphib", "This amphibian is a test");
        final BaseAmphibian actual = underTest.create(testAmphibian);
        assertThat(actual).isEqualTo(testAmphibian);
        assertThat(actual.getName()).isEqualTo(testAmphibian.getName());
        assertThat(actual.getDescription()).isEqualTo(testAmphibian.getDescription());
        assertThat(actual.getGroup()).isEqualTo(testAmphibian.getGroup());
    }

    @Test
    public void allShouldWork() throws Exception {
        underTest.create(amphibian);
        assertThat(underTest.all().size()).isEqualTo(1);
    }

    @Test
    public void getShouldWork() throws Exception {
        underTest.create(amphibian);
        final BaseAmphibian actual = underTest.get(amphibian.getId());
        assertThat(actual).isEqualTo(amphibian);
        assertThat(actual.getName()).isEqualTo(amphibian.getName());
        assertThat(actual.getDescription()).isEqualTo(amphibian.getDescription());
        assertThat(actual.getGroup()).isEqualTo(amphibian.getGroup());
    }
}
