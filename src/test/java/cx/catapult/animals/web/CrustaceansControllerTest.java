package cx.catapult.animals.web;

import cx.catapult.animals.domain.Crustacean;
import cx.catapult.animals.service.CrustaceansService;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
public class CrustaceansControllerTest extends BaseControllerTest {

    @Autowired
    private CrustaceansService crustaceansService;

    @BeforeEach
    public void setup() {
        animal = new Crustacean("James", "A crab");
        json = "{ \"name\": \"James\", \"description\": \"A crab\" }";
        url = "/api/1/crustaceans";
        id = new ArrayList<>(crustaceansService.all()).get(0).getId();
    }
}