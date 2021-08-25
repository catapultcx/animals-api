package cx.catapult.animals.web;

import cx.catapult.animals.domain.Dog;
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
public class DogsControllerTest {

    @Autowired
    private MockMvc mvc;

    private final Dog dog = new Dog("Spike", "A dog");
    private final String json = "{ \"name\": \"Spike\", \"description\": \"A dog\" }";

    @Test
    public void create() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/1/dogs").content(json).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
    }

    @Test
    public void all() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/1/dogs").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void get() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/1/dogs/123").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void delete() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/api/1/dogs/123").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void update() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/api/1/dogs/123").content(json).contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}