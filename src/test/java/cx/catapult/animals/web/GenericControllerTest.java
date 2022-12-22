package cx.catapult.animals.web;

import cx.catapult.animals.domain.Generic;
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
public class GenericControllerTest {

    @Autowired
    private MockMvc mvc;

    private Generic generic = new Generic("Tom", "Bob generic");
    private String json = "{ \"name\": \"Tom\", \"description\": \"Bob generic\" }";
    private String json2 = "{ \"name\": \"Macavity\", \"description\": \"Bob generic\" }";

    @Test
    public void all() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/1/generic").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void get() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/1/generic/123").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void create() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/1/generic").content(json).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
    }

    @Test
    public void delete() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/1/generic").content(json).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());

        mvc.perform(MockMvcRequestBuilders.delete("/api/1/generic/123").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void update() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/1/generic").content(json).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());

        mvc.perform(MockMvcRequestBuilders.put("/api/1/generic").content(json2).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

}