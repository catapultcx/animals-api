package cx.catapult.animals.web;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.jayway.jsonpath.JsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class AnimalControllerTest {

    @Autowired
    private MockMvc mvc;


    @Test
    public void all() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/1/animals").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getShouldWork() throws Exception {
        String json = "{ \"name\": \"Tom\", \"description\": \"Bob cat\", \"colour\": \"grey\", \"type\": \"amphibian\" }";
        MvcResult createResult = mvc.perform(MockMvcRequestBuilders.post("/api/1/animals").content(json).contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        String response = createResult.getResponse().getContentAsString();
        String id = JsonPath.parse(response).read("$.id");

        mvc.perform(MockMvcRequestBuilders.get("/api/1/animals/" + id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getShouldFail() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/1/animals/123").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Animal not found for id: 123"));
    }

    @Test
    public void create() throws Exception {
        String json = "{ \"name\": \"Tom\", \"description\": \"Bob cat\", \"colour\": \"grey\", \"type\": \"amphibian\" }";
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
        String json = "{ \"name\": \"Tom\", \"description\": \"Bob cat\", \"colour\": \"grey\", \"type\": \"amphibian\" }";
        MvcResult createResult = mvc.perform(MockMvcRequestBuilders.post("/api/1/animals").content(json).contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        String response = createResult.getResponse().getContentAsString();
        String id = JsonPath.parse(response).read("$.id");

        mvc.perform(MockMvcRequestBuilders.delete("/api/1/animals/" + id).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteShouldFail() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/api/1/animals/123").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Animal not found for id: 123"));
    }

    @Test
    public void filterShouldReturnEmptyResults() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/1/animals").param("name", "Test").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void filterShouldReturnResults() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/1/animals").param("name", "Tigger").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }
}
