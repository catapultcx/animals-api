package cx.catapult.animals.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import cx.catapult.animals.domain.BaseAnimal;
import cx.catapult.animals.domain.Group;
import cx.catapult.animals.service.AnimalsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AnimalsControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AnimalsService animalsService;

    @Test
    public void canGetAll() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/1/animals").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void canGetSpecificAnimal() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/1/animals/123").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void canCreateAnimal() throws Exception {
        BaseAnimal bob = new BaseAnimal("Bob", "Awkward", Group.MAMMALS, "Gerbil", "Beige");
        mvc.perform(MockMvcRequestBuilders.post("/api/1/animals").content(objectMapper.writeValueAsString(bob)).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
    }

    @Test
    public void canDeleteAnimal() throws Exception {
        BaseAnimal bob = new BaseAnimal("Bob", "Awkward", Group.MAMMALS, "Gerbil", "Beige");
        bob = animalsService.create(bob);

        mvc.perform(MockMvcRequestBuilders.delete("/api/1/animals/{id}", bob.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void cannotDeleteMissingAnimal() throws Exception {

        mvc.perform(MockMvcRequestBuilders.delete("/api/1/animals/{id}", UUID.randomUUID().toString()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void canUpdateAnimal() throws Exception {
        BaseAnimal bob = new BaseAnimal("Bob", "Awkward", Group.MAMMALS, "Gerbil", "Beige");
        bob = animalsService.create(bob);

        bob.setColour("Orange");
        mvc.perform(MockMvcRequestBuilders.put("/api/1/animals/{id}", bob.getId()).content(objectMapper.writeValueAsString(bob)).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        assertThat(animalsService.get(bob.getId()).getColour()).isEqualTo("Orange");
    }

    @Test
    public void cannotUpdateMissingAnimal() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/api/1/animals/{id}", UUID.randomUUID().toString()).content("{}").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }


}