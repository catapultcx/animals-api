package cx.catapult.animals.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import cx.catapult.animals.domain.BaseAnimal;
import cx.catapult.animals.domain.Group;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AnimalsControllerTest {

    @Autowired
    private MockMvc mvc;

    private BaseAnimal cat = new BaseAnimal("Tom", "Cat", "Grey","Bob cat", Group.MAMMALS);
    private String json = "{ \"name\": \"Tom\", \"description\": \"Bob cat\" }";

    private String updateJson = "{\"id\": \"Id1\", \"name\": \"Tom\", \"description\": \"Bob cat\" }";

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

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/api/1/animals")
                        .content(json).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated()).andReturn();

        ObjectMapper mapper = new ObjectMapper();
        BaseAnimal animal = mapper.readValue(mvcResult.getResponse().getContentAsString(), BaseAnimal.class);

        updateJson = updateJson.replace("Id1", animal.getId());

        mvc.perform(MockMvcRequestBuilders.put("/api/1/animals").content(updateJson).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void delete() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/api/1/animals/123").content(json).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }
}