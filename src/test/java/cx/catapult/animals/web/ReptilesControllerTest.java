package cx.catapult.animals.web;

import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.domain.Reptile;
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
public class ReptilesControllerTest {

    @Autowired
    private MockMvc mvc;

    private String json = "{ \"name\": \"Leo\", \"description\": \"Leo lizard\" }";

    @Test
    public void create() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/1/reptiles").content(json).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
    }
}
