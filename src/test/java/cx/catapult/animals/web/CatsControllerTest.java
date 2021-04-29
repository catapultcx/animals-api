package cx.catapult.animals.web;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * A {@link SpringBootTest} for the {@link CrustaceansController}.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class CatsControllerTest {

	private static final String URI_PATH = "/api/1/cats";

	@Autowired
    private MockMvc mvc;

    private String json = "{ \"name\": \"Tom\", \"description\": \"Bob cat\" }";

    @Test
    public void all() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(URI_PATH).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void get() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(URI_PATH + "/123").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void create() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post(URI_PATH).content(json).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
    }
}