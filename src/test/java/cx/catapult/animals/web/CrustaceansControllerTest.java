package cx.catapult.animals.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import cx.catapult.animals.domain.Crustacean;
import cx.catapult.animals.service.CrustaceansService;

/**
 * A {@link SpringBootTest} for the {@link CrustaceansController}.
 */
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class CrustaceansControllerTest {

	private static final String URI_PATH = "/api/1/crustaceans";

	@Autowired
	private MockMvc mvc;

	@MockBean
	private CrustaceansService service;

	private String json = "{ \"name\": \"Crabby\", \"description\": \"Crabby the crab\" }";

	@Test
	public void create() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post(URI_PATH)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isCreated());
	}

	@Test
	public void all() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get(URI_PATH)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void get() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get(URI_PATH + "/123")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void update() throws Exception {
		Crustacean updatedCrustacean = new Crustacean("Colin", "Colin the crab");
		String updatedJson = "{ \"name\": \"Colin\", \"description\": \"Colin the crab\" }";
		given(service.update("123", updatedCrustacean)).willReturn(updatedCrustacean);

		mvc.perform(MockMvcRequestBuilders.put(URI_PATH + "/123")
				.content(updatedJson)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNoContent());
	}

	@Test
	public void updateUnknownCrustacean() throws Exception {
		Crustacean updatedCrustacean = new Crustacean("Colin", "Colin the crab");
		String updatedJson = "{ \"name\": \"Colin\", \"description\": \"Colin the crab\" }";
		given(service.update("123", updatedCrustacean)).willReturn(null);

		mvc.perform(MockMvcRequestBuilders.put(URI_PATH + "/123")
				.content(updatedJson)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNotFound());
	}

	@Test
	public void delete() throws Exception {
		given(service.delete("123")).willReturn(true);

		mvc.perform(MockMvcRequestBuilders.delete(URI_PATH + "/123").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void deleteUnknownCrustacean() throws Exception {
		given(service.delete("123")).willReturn(false);

		mvc.perform(MockMvcRequestBuilders.delete(URI_PATH + "/123").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
}