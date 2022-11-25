package cx.catapult.animals.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AnimalControllerTest {

    @Autowired
    private MockMvc mvc;
    private final static String CAT_JSON = "{ \"@type\": \"Cat\", \"name\": \"Tom\", \"description\": \"Bob cat\", \"colour\": \"Orange\" }";
    private final static String IGUANA_JSON = "{ \"@type\": \"Iguana\", \"name\": \"Tom\", \"description\": \"Bob iguana\", \"colour\": \"Green\" }";

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    void all() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/1/animals").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void get() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/1/animals/123").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void testCreateWithAnimalTypeCat() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/1/animals").content(CAT_JSON).contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isCreated());
    }

    @Test
    void testCreateWithAnimalTypeIguana() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/1/animals").content(IGUANA_JSON).contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isCreated());
    }
}