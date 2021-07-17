package cx.catapult.animals.web;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cx.catapult.animals.domain.Cat;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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

    @Test
    public void delete() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete(String.format("/api/1/%s/123", getUrlSuffix())).accept(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk());
    }

}