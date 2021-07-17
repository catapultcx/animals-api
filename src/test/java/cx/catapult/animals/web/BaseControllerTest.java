package cx.catapult.animals.web;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cx.catapult.animals.domain.Animal;
import cx.catapult.animals.domain.Cat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public abstract class BaseControllerTest<T extends Animal> {

    @Autowired
    protected MockMvc mvc;

    protected abstract String getExpectedJson();
    protected abstract String getUrlSuffix();

    @Test
    public void all() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(String.format("/api/1/%s", getUrlSuffix())).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void get() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(String.format("/api/1/%s/123", getUrlSuffix())).accept(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk());
    }

    @Test
    public void create() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post(String.format("/api/1/%s", getUrlSuffix())).content(getExpectedJson()).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
    }
}