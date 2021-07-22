package cx.catapult.animals.exception;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ApiExceptionHandlerTest {

    @Autowired
    private MockMvc mvc;

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

    @Test
    void handleNotFoundException() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/1/horses/1").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }
}