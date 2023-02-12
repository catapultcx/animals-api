package cx.catapult.animals.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import cx.catapult.animals.domain.Animal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AnimalControllerTest {

    ObjectMapper objectMapper = new ObjectMapper();

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
        String content = mvc.perform(MockMvcRequestBuilders.post("/api/1/animals")
                        .content(json).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn().getResponse().getContentAsString();
        Animal updateAnimal = objectMapper.readValue(content, Animal.class);
        updateAnimal.setColour("Black");
        mvc.perform(MockMvcRequestBuilders.put("/api/1/animals")
                        .content(objectMapper.writeValueAsString(updateAnimal))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.colour").value("Black"));
    }

    @Test
    public void delete() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/api/1/animals/123").content(json)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void filterByName() throws Exception {
        String response = mvc.perform(MockMvcRequestBuilders.get("/api/1/animals/filter?name=Smelly")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        List<Animal> animals =  objectMapper.readValue(response, new TypeReference<>() {});
        assertEquals(1, animals.size());
        assertEquals("Fish", animals.get(0).getType());
    }

    @Test
    public void filterByColourAndType() throws Exception {
        String response = mvc.perform(MockMvcRequestBuilders
                        .get("/api/1/animals/filter?colour=Gold&type=Fish")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        List<Animal> animals =  objectMapper.readValue(response, new TypeReference<>(){});
        assertEquals(1, animals.size());
        assertEquals("Quicker on water", animals.get(0).getDescription());
        assertEquals("Smelly", animals.get(0).getName());
    }
}
