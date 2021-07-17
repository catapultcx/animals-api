package cx.catapult.animals.web;

import cx.catapult.animals.domain.Cat;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
public class ArachnidsControllerTest extends BaseControllerTest<Cat> {

    private final String json = "{ \"name\": \"Spider McSpiderface\", \"description\": \"Hairy\" }";

    @Override
    protected String getExpectedJson() {
        return json;
    }

    @Override
    protected String getUrlSuffix() {
        return "arachnids";
    }
}