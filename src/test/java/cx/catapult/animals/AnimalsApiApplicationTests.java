package cx.catapult.animals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		properties = {
				"spring.datasource.url=jdbc:h2:mem:testdb"
		})
class AnimalsApiApplicationTests {

	@Test
	void contextLoads() {
	}

}
