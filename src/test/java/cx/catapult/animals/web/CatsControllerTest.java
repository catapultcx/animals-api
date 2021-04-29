package cx.catapult.animals.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.service.CatsService;

/**
 * A {@link SpringBootTest} for the {@link CrustaceansController}.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class CatsControllerTest {

	private static final String URI_PATH = "/api/1/cats";

	@Autowired
    private MockMvc mvc;

	@MockBean
	private CatsService service;

    private String json = "{ \"name\": \"Tom\", \"description\": \"Bob cat\" }";

    @Test
    public void all() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(URI_PATH).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void get() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(URI_PATH + "/123").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void create() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post(URI_PATH).content(json).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
    }

	@Test
	public void update() throws Exception {
		Cat updatedCat = new Cat("Scratchy", "Scratchy cat");
		String updatedJson = "{ \"name\": \"Scratchy\", \"description\": \"Scratchy cat\" }";
		given(service.update("123", updatedCat)).willReturn(updatedCat);

		mvc.perform(MockMvcRequestBuilders.put(URI_PATH + "/123")
				.content(updatedJson)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNoContent());
	}

	@Test
	public void updateUnknownCat() throws Exception {
		Cat updatedCat = new Cat("Scratchy", "Scratchy cat");
		String updatedJson = "{ \"name\": \"Scratchy\", \"description\": \"Scratchy cat\" }";
		given(service.update("123", updatedCat)).willReturn(null);

		mvc.perform(MockMvcRequestBuilders.put(URI_PATH + "/123")
				.content(updatedJson)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNotFound());
	}
}