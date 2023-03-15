package cx.catapult.animals.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AnimalControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void create() throws Exception {
        String json = "{ \"name\": \"MM1\", \"description\": \"a good cow\", \"color\": \"white\", \"type\": \"MAMMALS\" }";
        mvc.perform(MockMvcRequestBuilders.post("/api/1/animals").content(json).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.name", is("MM1")))
                .andExpect(jsonPath("$.description", is("a good cow")))
                .andExpect(jsonPath("$.color", is("white")))
                .andExpect(jsonPath("$.type", is("MAMMALS")));
    }

    @Test
    public void testGet() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/1/animals/123").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testAll() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/1/animals").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDelete() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/1/animals/123").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
