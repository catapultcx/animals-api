package cx.catapult.animals.web;

import com.google.gson.Gson;
import cx.catapult.animals.domain.Cat;
import org.assertj.core.api.Assertions;
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
public class CatsControllerTest {

    @Autowired
    private MockMvc mvc;

    private final Cat cat = new Cat("Tom", "Bob cat");
    private final String json = "{ \"name\": \"Tom\", \"description\": \"Bob cat\" }";

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
    public void deleteById() throws Exception {
        Cat createdCat = createCat();

        mvc.perform(MockMvcRequestBuilders.delete("/api/1/cats/" + createdCat.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void update() throws Exception {
        Cat createdCat = createCat();
        createdCat.setDescription("Update desc");

        Gson g = new Gson();
        String toJson = g.toJson(createdCat);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put("/api/1/cats")
                        .content(toJson)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();

        String responseContent = mvcResult.getResponse().getContentAsString();
        Cat updatedCat = g.fromJson(responseContent, Cat.class);
        Assertions.assertThat(updatedCat.getDescription()).isEqualTo("Update desc");
    }

    private Cat createCat() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/api/1/cats").content(json).contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        Gson gson = new Gson();
        return gson.fromJson(contentAsString, Cat.class);
    }
}