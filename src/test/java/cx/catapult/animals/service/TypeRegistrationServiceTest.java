package cx.catapult.animals.service;

import cx.catapult.animals.domain.Group;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class TypeRegistrationServiceTest {

    @Autowired
    TypeRegistrationService typeService;


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
