package cx.catapult.animals.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static cx.catapult.animals.web.CatsMapping.CATS_API_V1;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CatsControllerTest {

    @Autowired
    private MockMvc mvc;

    private final String TOM_CAT_JSON = "{ \"name\": \"Tom\", \"description\": \"Bob cat\" }";

    @Test
    public void shouldLoadAll() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(CATS_API_V1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldLoadById() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(CATS_API_V1 + "/123")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldCreate() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post(CATS_API_V1)
                        .content(TOM_CAT_JSON).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldUpdate() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post(CATS_API_V1 + "/123")
                        .content(TOM_CAT_JSON).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteById() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete(CATS_API_V1 + "/123")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldFilterWithoutParams() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(CATS_API_V1 + "/filter")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldFilterWithQueryParams() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(CATS_API_V1 + "/filter")
                        .param("query", "some query")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}