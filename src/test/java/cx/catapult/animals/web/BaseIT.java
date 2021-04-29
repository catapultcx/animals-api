package cx.catapult.animals.web;

import cx.catapult.animals.AnimalsApiApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

@SpringBootTest(
    classes = AnimalsApiApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration")
public abstract class BaseIT {
  private static final MySQLContainer mySQLContainer;

  static {
    mySQLContainer =
        (MySQLContainer)
            (new MySQLContainer("mysql:8.0")
                .withUsername("testcontainers")
                .withPassword("Testcontain3rs!")
                .withReuse(true));
    mySQLContainer.start();
  }

  @DynamicPropertySource
  public static void setDatasourceProperties(final DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
    registry.add("spring.datasource.password", mySQLContainer::getPassword);
    registry.add("spring.datasource.username", mySQLContainer::getUsername);
  }

  @Autowired public TestRestTemplate restTemplate;
}
