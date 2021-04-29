package cx.catapult.animals.domain;

import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

public class CrustaceanTest {

	@Test
	public void equalsContract() {
		EqualsVerifier.simple().forClass(Crustacean.class).verify();
	}

}