package cx.catapult.animals.web;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class AmphibianControllerTest {

    private final String json = "{ \"name\": \"Test Amphib\", \"description\": \"An amphib that likes to be tested\" }";

    @Autowired
    private MockMvc mvc;

    @Test
    public void create() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/1/amphibians").content(json).contentType(MediaType.APPLICATION_JSON_VALUE))
           .andExpect(status().isCreated());
    }

    @Test
    public void all() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/1/amphibians").accept(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk());
    }

    @Test
    public void get() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/1/amphibians/456").accept(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk());
    }
}
