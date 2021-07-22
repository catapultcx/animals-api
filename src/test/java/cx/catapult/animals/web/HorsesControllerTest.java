package cx.catapult.animals.web;

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
class HorsesControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void create() throws Exception {
        String json = "{ \"name\": \"Spirit\", \"description\": \"Stallion\" }";
        mvc.perform(MockMvcRequestBuilders.post("/api/1/horses").content(json).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
    }

    @Test
    public void get() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/1/horses/123").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void all() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/1/horses").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void handleValidationExceptions() throws Exception {
        String json = "{ \"description\": \"Stallion\" }";
        mvc.perform(MockMvcRequestBuilders.post("/api/1/horses").content(json).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    void handleConstraintViolationException() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/1/horses/ ").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

}