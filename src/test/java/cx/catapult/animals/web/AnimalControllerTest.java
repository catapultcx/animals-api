package cx.catapult.animals.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import cx.catapult.animals.domain.Animal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class AnimalControllerTest {

    @Autowired
    private MockMvc mvc;

    private String json = "{\n" +
            "  \"name\": \"Tom\",\n" +
            "  \"description\": \"Bob Cat\",\n" +
            "  \"colour\": \"white\",\n" +
            "  \"type\": \"Cat\"\n" +
            "}";

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
        mvc.perform(MockMvcRequestBuilders.post("/api/1/animals")
                        .content(json).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
    }

    @Test
    public void update() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String content = mvc.perform(MockMvcRequestBuilders.post("/api/1/animals")
                        .content(json).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn().getResponse().getContentAsString();
        Animal updateAnimal = objectMapper.readValue(content, Animal.class);
        updateAnimal.setColour("Black");
        mvc.perform(MockMvcRequestBuilders.put("/api/1/animals")
                        .content(objectMapper.writeValueAsString(updateAnimal)).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }
}
