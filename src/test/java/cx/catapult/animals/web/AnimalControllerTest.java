package cx.catapult.animals.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import cx.catapult.animals.domain.Animal;
import cx.catapult.animals.domain.Cat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AnimalControllerTest {

    @Autowired
    private MockMvc mvc;
    private String json = "{ \"@type\": \"Cat\", \"name\": \"Tom\", \"description\": \"Bob cat\", \"colour\": \"Orange\" }";

    private static final ObjectMapper MAPPER = new ObjectMapper();

    {
        MAPPER.registerSubtypes(new NamedType(Cat.class, "Cat"));
    }

    @Test
    public void all() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/1/animals").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    public void get() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/1/animals/123").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
    @Test
    void testCreateWithAnimalType() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/1/animals").content(json).contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isCreated());
    }
}