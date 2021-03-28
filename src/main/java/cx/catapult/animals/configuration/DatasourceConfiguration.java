package cx.catapult.animals.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@ConditionalOnProperty(prefix = "storage", name = "persistant", havingValue = "false")
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class DatasourceConfiguration {
}
