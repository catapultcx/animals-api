package cx.catapult.animals.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import cx.catapult.animals.domain.Crustacean;

/**
 * Unit test for {@link CrustaceansService}.
 */
public class CrustaceansServiceTest {

	CrustaceansService service = new CrustaceansService();

	@Test
	public void createShouldWork() {
		Crustacean thisCrab = new Crustacean();
		thisCrab.setName("Crabby");
		thisCrab.setDescription("Crabby the Crab");

		Crustacean actual = service.create(thisCrab);

		assertThat(actual).isEqualTo(thisCrab);
		assertThat(actual.getName()).isEqualTo(thisCrab.getName());
		assertThat(actual.getDescription()).isEqualTo(thisCrab.getDescription());
		assertThat(actual.getGroup()).isEqualTo(thisCrab.getGroup());
	}
}