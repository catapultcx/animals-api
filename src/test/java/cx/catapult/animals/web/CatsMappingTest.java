package cx.catapult.animals.web;

import org.junit.jupiter.api.Test;

import static cx.catapult.animals.web.CatsMapping.CATS_API_V1;
import static org.assertj.core.api.Assertions.assertThat;

public class CatsMappingTest {

    @Test
    public void shouldCreateClassToMakeTestCoverageHappy() {
        CatsMapping catsMapping = new CatsMapping();
        assertThat(catsMapping).isNotNull();
    }

    @Test
    public void shouldTestMapping() {
        assertThat(CATS_API_V1).isEqualTo("/api/1/cats");
    }

}