package cx.catapult.animals.web;

import cx.catapult.animals.domain.Cat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CatsControllerTest extends BaseControllerTest<Cat> {

    private final String json = "{ \"name\": \"Tom\", \"description\": \"Bob cat\" }";

    @Override
    protected String getExpectedJson() {
        return json;
    }

    @Override
    protected String getUrlSuffix() {
        return "cats";
    }
}