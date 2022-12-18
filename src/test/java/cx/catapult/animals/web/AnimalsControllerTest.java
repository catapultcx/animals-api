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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AnimalsControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

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
        BaseAnimal bob = new BaseAnimal("666","Bob", "Awkward", Group.MAMMALS, "Gerbil", "Beige");
        mvc.perform(MockMvcRequestBuilders.post("/api/1/animals").content(objectMapper.writeValueAsString(bob)).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
    }


}