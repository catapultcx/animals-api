package cx.catapult.animals.web;

import com.jayway.jsonpath.JsonPath;
import cx.catapult.animals.domain.Animal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AnimalControllerTest {

    @Autowired
    private MockMvc mvc;

    private Animal animal = new Animal("Tom", "Bob cat", "grey", "mammals");
    private String json = "{ \"name\": \"Tom\", \"description\": \"Bob cat\", \"colour\": \"grey\", \"type\": \"amphibian\" }";

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
    public void create() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/1/animals").content(json).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
    }

    @Test
    public void update() throws Exception {
        String json = "{ \"name\": \"Tom\", \"description\": \"Bob cat\", \"colour\": \"grey\", \"type\": \"amphibian\" }";
        MvcResult createResult = mvc.perform(MockMvcRequestBuilders.post("/api/1/animals").content(json).contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        String response = createResult.getResponse().getContentAsString();
        String id = JsonPath.parse(response).read("$.id");

        String updatedJson = String.format("{ \"id\": \"%s\", \"name\": \"Tom\", \"description\": \"Bob cat\", \"colour\": \"grey\", \"type\": \"amphibian\" }", id);
        mvc.perform(MockMvcRequestBuilders.put("/api/1/animals").content(updatedJson).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteShouldWork() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/api/1/animals/123").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }
}