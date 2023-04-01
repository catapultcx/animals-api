package cx.catapult.animals.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import cx.catapult.animals.domain.Cat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CatsControllerTest {

    @Autowired
    private MockMvc mvc;

    private Cat cat = new Cat("Tom", "Bob cat");
    private String json = "{ \"name\": \"Tom\", \"description\": \"Bob cat\" }";

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void all() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/1/cats").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void get() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/1/cats/123").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void create() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/1/cats").content(json).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
    }


    @Test
    public void update() throws Exception {
        json = "{ \"name\": \"Tom updated\", \"description\": \"Bob cat updated\" }";
        mvc.perform(MockMvcRequestBuilders.put("/api/1/cats/update").content(json).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }


    @Test
    public void delete() throws Exception {

        String response = mvc.perform(MockMvcRequestBuilders.post("/api/1/cats").content(json).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
        Cat created = objectMapper.readValue(response, Cat.class);
        String id = created.getId();

        mvc.perform(MockMvcRequestBuilders.delete("/api/1/cats/" + id).content(json).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        String invalidId = "some non existing id";
        mvc.perform(MockMvcRequestBuilders.delete("/api/1/cats/" + invalidId).content(json).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

    }

    @Test
    public void search() throws Exception {

        String response = mvc.perform(MockMvcRequestBuilders.get("/api/1/cats").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        Collection<Cat> cats = objectMapper.readValue(response, Collection.class);
        String nameQueryString = "Tiger";
        String descQueryString = "";
        String searchResponse = mvc.perform(MockMvcRequestBuilders.get("/api/1/cats/search")
                        .param("name", nameQueryString).param("desc", descQueryString))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        Collection<Cat> searchedCats = objectMapper.readValue(searchResponse, new TypeReference<Collection<Cat>>() {
        });
        assertThat(searchedCats).hasSize(1);

        Cat resultCat = searchedCats.iterator().next();
        assertThat(resultCat.getName()).isEqualTo("Tiger");
        assertThat(resultCat.getDescription()).isEqualTo("Large cat");
    }
}