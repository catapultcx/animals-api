package cx.catapult.animals.web;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cx.catapult.animals.domain.Animal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public abstract class BaseControllerTest {

    @Autowired
    protected MockMvc mvc;

    protected String id;

    protected Animal animal;
    protected String json;
    protected String url;

    @Test
    public void all() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk());
    }

    @Test
    public void get() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(url + "/" + id))
           .andExpect(status().isOk());
    }

    @Test
    public void create() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post(url).content(json).contentType(MediaType.APPLICATION_JSON_VALUE))
           .andExpect(status().isCreated());
    }

    @Test
    public void delete() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete(url + "/" + id))
           .andExpect(status().isNoContent());
    }

    @Test
    public void update() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post(url + "/" + id)
                                          .content(json)
                                          .contentType(MediaType.APPLICATION_JSON_VALUE))
           .andExpect(status().isCreated());
    }
}
