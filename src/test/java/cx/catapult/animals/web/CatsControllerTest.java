package cx.catapult.animals.web;

import cx.catapult.animals.domain.Cat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static cx.catapult.animals.TestUtils.convertStringToObject;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CatsControllerTest {

    @Autowired
    private MockMvc mvc;

    private Cat cat = new Cat("Tom", "Bob cat");
    private String json = "{ \"name\": \"Tom\", \"description\": \"Bob cat\" }";

    @Test
    void all() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/1/cats").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void get() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/api/1/cats").content(json).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        Cat cat = (Cat)convertStringToObject(result.getResponse().getContentAsString(), Cat.class);

        mvc.perform(MockMvcRequestBuilders.get("/api/1/cats/"+cat.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void create() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/1/cats").content(json).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
    }

    @Test
    public void update() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/api/1/cats").content(json).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        mvc.perform(MockMvcRequestBuilders.put("/api/1/cats/").content(result.getResponse().getContentAsString()).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isAccepted());
    }
}