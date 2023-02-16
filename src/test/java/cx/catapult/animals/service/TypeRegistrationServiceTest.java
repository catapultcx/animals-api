package cx.catapult.animals.service;

import cx.catapult.animals.domain.Group;
import cx.catapult.animals.configuration.AnimalFactoryConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import out.TestFactory;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class TypeRegistrationServiceTest {
    private static TypeRegistrationService typeService;

    @BeforeAll
    public static void setUp() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(TestFactory.class, AnimalFactoryConfiguration.class, TypeRegistrationService.class);
        context.refresh();
        typeService = context.getBean(TypeRegistrationService.class);
        assertThat(typeService).isNotNull();
    }

    @Test
    void typeRegistrationService_whenCalledRegisterType_shouldRegisterANewType() {
        List<String> types = typeService.getRegisteredTypes();
        assertThat(types.size()).isEqualTo(9);

        assertThat(typeService.registerType("monkey", Group.MAMMALS))
                .isTrue();
        List<String> newTypes = typeService.getRegisteredTypes();

        assertThat(newTypes.size()).isEqualTo(10);
        assertThat(newTypes.contains("monkey")).isTrue();
    }

    @Test
    void typeRegistrationService_whenCalledToRegisterAnExistingType_shouldFail() {
        assertThat(typeService.registerType("monkey", Group.MAMMALS)).isFalse();
    }

    @Test
    void typeRegistrationService_whenCalledToListGroups_shouldList() {
        assertThat(typeService.getGroups()).isEqualTo(Arrays.stream(Group.values()).toList());
    }

}
