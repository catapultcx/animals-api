package cx.catapult.animals.domain;

import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

public class CatTest {

	@Test
	public void equalsContract() {
		EqualsVerifier.simple().forClass(Cat.class).verify();
	}

}