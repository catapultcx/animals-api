package cx.catapult.animals.web;

import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.service.CatsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CatsControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CatsService catsService;

    private String json = "{ \"name\": \"Tom\", \"description\": \"Bob cat\" }";

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
    public void delete() throws Exception {
        Cat cat = new Cat("Tom", "Bob cat");
        catsService.create(cat);
        mvc.perform(MockMvcRequestBuilders.delete("/api/1/cats/" + cat.getId()).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void update() throws Exception {
        Cat cat = new Cat("Tom", "Bob cat");
        catsService.create(cat);
        String json = String.format("""
            {
                "id": "%s",
                "name": "%s",
                "description": "%s",
                "group": "%s"
            }
        """, cat.getId(), "Modified", cat.getDescription(), cat.getGroup());
        catsService.create(cat);
        mvc.perform(MockMvcRequestBuilders.put("/api/1/cats").content(json).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }
}