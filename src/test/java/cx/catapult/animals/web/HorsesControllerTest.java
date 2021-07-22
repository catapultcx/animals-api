package cx.catapult.animals.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import cx.catapult.animals.domain.Horse;
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
class HorsesControllerTest {

    @Autowired
    private MockMvc mvc;

    private String json = "{ \"name\": \"Spirit\", \"description\": \"Stallion\" }";

    @Test
    void create() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/1/horses").content(json).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
    }

    @Test
    public void get() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/api/1/horses").content(json).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        Horse horse = (Horse)convertStringToObject(result.getResponse().getContentAsString(), Horse.class);

        mvc.perform(MockMvcRequestBuilders.get("/api/1/horses/"+horse.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void all() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/1/horses").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void delete() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/api/1/horses").content(json).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        Horse horse = (Horse)convertStringToObject(result.getResponse().getContentAsString(), Horse.class);

        mvc.perform(MockMvcRequestBuilders.delete("/api/1/horses/"+horse.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}