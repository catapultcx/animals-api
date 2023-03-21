package cx.catapult.animals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AnimalsApiApplicationTests {

	@Test
	void contextLoads() {
	}

	// required to cover main method coverage
	@Test
	public void main() {
		AnimalsApiApplication.main(new String[] {});
	}

}
