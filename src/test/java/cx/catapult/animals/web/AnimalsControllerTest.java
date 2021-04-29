package cx.catapult.animals.web;

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

import cx.catapult.animals.domain.BaseAnimal;
import cx.catapult.animals.service.AnimalsService;

/**
 * A {@link SpringBootTest} for the {@link AnimalsController}.
 */
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class AnimalsControllerTest {
	private static final String URI_PATH = "/api/1/animals";

	@Autowired
	private MockMvc mvc;

	@MockBean
	private AnimalsService<BaseAnimal> service;

	@Test
	public void get() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get(URI_PATH + "/123").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void getByName() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get(URI_PATH + "?name=Tom").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
}