package cx.catapult.animals.web;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AnimalsControllerTest {

    @Autowired
    private MockMvc mvc;
    private final String json = "{ \"name\": \"Tom\", \"description\": \"Bob cat\" }";
    private final String jsonWithColor = "{ \"name\": \"Tom\", \"description\": \"Bob cat\", \"color\": \"red\" }";

    @Test
    public void all() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/2/cats").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void get() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/2/cats/123").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void create() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/2/cats").content(jsonWithColor).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
    }

    @Test
    public void animalController_whenDeleteRequestSent_shouldDelete() throws Exception {
        String result = mvc.perform(MockMvcRequestBuilders.post("/api/2/cats")
                        .content(jsonWithColor)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String id = JsonPath.read(result, "$.id");

        mvc.perform(MockMvcRequestBuilders.delete("/api/2/cats/" + id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void animalController_whenPutRequestSent_shouldUpdate() throws Exception {
        String result = mvc.perform(MockMvcRequestBuilders.post("/api/2/cats")
                        .content(jsonWithColor)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String id = JsonPath.read(result, "$.id");

        mvc.perform(MockMvcRequestBuilders.put("/api/2/cats/" + id).content(result)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void animalController_whenPutRequestSentWithAndUnknownId_shouldSendNotFound() throws Exception {
        String jsonWithColor = "{ \"id\":\"unknown\", \"name\": \"Tom\", \"description\": \"Bob cat\", \"color\": \"red\" }";
        mvc.perform(MockMvcRequestBuilders.put("/api/2/cats/unknown").content(jsonWithColor)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void animalController_whenPutRequestSentWithAndMismatched_shouldSendBadRequest() throws Exception {
        String jsonWithColor = "{ \"id\":\"different\", \"name\": \"Tom\", \"description\": \"Bob cat\", \"color\": \"red\" }";
        mvc.perform(MockMvcRequestBuilders.put("/api/2/cats/unknown").content(jsonWithColor)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void animalController_whenColorIsNull_createShouldFail() throws Exception {

        mvc.perform(MockMvcRequestBuilders.post("/api/2/cats").content(json).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void animalController_whenColorIsInvalid_createShouldFail() throws Exception {
        String jsonWithInvalidColor = "{ \"name\": \"Tom\", \"description\": \"Bob cat\", \"color\": \"1312\" }";
        mvc.perform(MockMvcRequestBuilders.post("/api/2/cats")
                        .content(jsonWithInvalidColor)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void animalController_whenCalledToAnUnsupportedEndpoint_ShouldThrowBadRequest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/2/bees/124")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void animalController_whenCalledToFilter_ShouldFilter() throws Exception {
        String query = "?names=Tom,Jerry,gg&colors=blue&types=cat";
        mvc.perform(MockMvcRequestBuilders.get("/api/2/filter" + query).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }
}
