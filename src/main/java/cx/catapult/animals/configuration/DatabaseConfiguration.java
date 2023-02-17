package cx.catapult.animals.configuration;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "cx.catapult.animals.repository")
public class DatabaseConfiguration {

}
