package cx.catapult.animals.web;

import static java.lang.String.format;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import com.fasterxml.jackson.databind.ObjectMapper;
import cx.catapult.animals.domain.Bird;
import cx.catapult.animals.service.BirdsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(MockitoExtension.class)
class BirdsControllerTest {

  private static final String ID = "ff8080817b6fd27e017b6fd282bb0000";
  private static final String BIRDS_URL = "/api/1/birds";
  private static final String FORMATTED_URL = format("%s/%s", BIRDS_URL, ID);
  private PodamFactory podamFactory;
  private ObjectMapper mapper;
  private MockMvc mockMvc;
  @InjectMocks
  private BirdsController classUnderTest;

  @Mock
  private BirdsService mockBirdsService;

  @BeforeEach
  void setUp() {
    mapper = new ObjectMapper();
    podamFactory = new PodamFactoryImpl();
    mockMvc = standaloneSetup(classUnderTest).build();
  }

  @Test
  void all_happyPath_ableToFetchAllTheRecord() throws Exception {
    mockMvc.perform(get(BIRDS_URL)
        .contentType(APPLICATION_JSON))
           .andDo(print())
           .andExpect(status().isOk());
    verify(mockBirdsService).all();
  }

  @Test
  void get_happyPath_ableToFetchRecord() throws Exception {
    mockMvc.perform(get(FORMATTED_URL)
        .contentType(APPLICATION_JSON))
           .andDo(print())
           .andExpect(status().isOk());
    verify(mockBirdsService).get(ID);
  }

  @Test
  void create_happyPath_manageToCreateRecord() throws Exception {
    final Bird bird = podamFactory.manufacturePojo(Bird.class);

    mockMvc.perform(post(BIRDS_URL).content(mapper.writeValueAsString(bird))
                                   .contentType(APPLICATION_JSON))
           .andDo(print())
           .andExpect(status().isCreated());
    verify(mockBirdsService).create(any(Bird.class));
  }

  @Test
  void update_happyPath_manageToUpdateRecord() throws Exception {
    final Bird bird = podamFactory.manufacturePojo(Bird.class);
    mockMvc.perform(put(FORMATTED_URL).content(mapper.writeValueAsString(bird))
                                      .contentType(APPLICATION_JSON))
           .andDo(print())
           .andExpect(status().isOk());
    verify(mockBirdsService).update(anyString(), any(Bird.class));
  }

  @Test
  void delete_happyPath_manageToDeleteRecord() throws Exception {
    mockMvc.perform(delete(FORMATTED_URL).contentType(APPLICATION_JSON))
           .andDo(print())
           .andExpect(status().isOk());
    verify(mockBirdsService).delete(ID);
  }
}
