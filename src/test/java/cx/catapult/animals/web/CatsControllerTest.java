package cx.catapult.animals.web;

import cx.catapult.animals.domain.Cat;

import cx.catapult.animals.service.CatsService;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@AutoConfigureMockMvc
public class CatsControllerTest extends BaseControllerTest {
    @Autowired
    private CatsService catsService;

    @BeforeEach
    public void setup() {
        animal = new Cat("Tom", "Bob cat");
        json = "{ \"name\": \"Tom\", \"description\": \"Bob cat\" }";
        url = "/api/1/cats";
        id = new ArrayList<>(catsService.all()).get(0).getId();
    }
}