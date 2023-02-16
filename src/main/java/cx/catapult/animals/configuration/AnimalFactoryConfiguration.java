package cx.catapult.animals.configuration;

import cx.catapult.animals.domain.Group;
import cx.catapult.animals.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "animal")
public class AnimalFactoryConfiguration {
    private Map<Group, List<String>> types = new HashMap<>();

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) this.applicationContext.getAutowireCapableBeanFactory();
        types.keySet().forEach(group -> types.get(group).forEach(type -> {
            AnimalService bean = new AnimalService(type, group);
            beanFactory.registerSingleton("%ss".formatted(type), bean);
        }));

    }

    /**
     *
     * Keeping cats bean for backward compatible Cats service.
     */
    @Scope("singleton")
    @Bean("cats")
    public AnimalService getCatsService() {
        return new AnimalService("cat", Group.MAMMALS);
    }


    public Map<Group, List<String>> getTypes() {
        return types;
    }

    public void setTypes(Map<Group, List<String>> types) {
        this.types = types;
    }
}
