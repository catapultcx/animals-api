package cx.catapult.animals.web;

import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.repository.CrustaceanRepository;
import cx.catapult.animals.service.CatsService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = CatsController.class)
public class CatsControllerTest extends BaseControllerTest {

  @MockBean
  private CatsService catsService;

  private Cat cat = new Cat("Tom", "Bob cat");
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
    mvc.perform(
            MockMvcRequestBuilders.post("/api/1/cats")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isCreated());
  }
}
