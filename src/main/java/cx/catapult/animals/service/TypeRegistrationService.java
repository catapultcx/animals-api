package cx.catapult.animals.service;

import cx.catapult.animals.domain.Group;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Service
public class TypeRegistrationService {
    private final ApplicationContext applicationContext;
    private  DefaultListableBeanFactory beanFactory;

    private final Logger logger = LoggerFactory.getLogger(TypeRegistrationService.class);
    public TypeRegistrationService(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void init() {
        beanFactory = (DefaultListableBeanFactory) this.applicationContext.getAutowireCapableBeanFactory();
    }

    public boolean registerType(String type, Group group) {
        try {
            AnimalService bean = new AnimalService(type, group);
            beanFactory.registerSingleton("%ss".formatted(type), bean);
            return true;
        } catch (Exception e) {
            logger.error("Error registering type %s to group %s".formatted(type, group), e);
            return false;
        }
    }

    public List<String> getRegisteredTypes() {
        return Arrays.stream(applicationContext.getBeanNamesForType(AnimalService.class))
                .map(k -> k.substring(0, k.length() -1))
                .toList();
    }

    public List<Group> getGroups() {
        return Arrays.stream(Group.values()).toList();
    }
}
