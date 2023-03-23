package cx.catapult.animals.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
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
import cx.catapult.animals.exception.AnimalNotFoundException;
import cx.catapult.animals.service.CatsService;

@SpringBootTest
@AutoConfigureMockMvc
public class CatsControllerTest {

    @Autowired
    private MockMvc mvc;
    
    @MockBean
    private CatsService service;

    private Cat cat = new Cat("Tom", "Bob cat");
    private String json = "{ \"name\": \"Tom\", \"description\": \"Bob cat\" }";

    @Test
    public void all() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/1/cats").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void get() throws Exception {
    	cat.setId("123");
    	
    	when(service.get("123")).thenReturn(cat);
    	
        mvc.perform(MockMvcRequestBuilders.get("/api/1/cats/123").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getFail() throws Exception {
    	cat.setId("123");
    	
    	when(service.get("123")).thenThrow(AnimalNotFoundException.class);
    	
        mvc.perform(MockMvcRequestBuilders.get("/api/1/cats/123").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    
    @Test
    public void create() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/1/cats").content(json).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
    }
    
    @Test
    public void createFail() throws Exception {
    	doThrow(IllegalArgumentException.class).when(service).create(any(Cat.class));
    	
    	
    	mvc.perform(MockMvcRequestBuilders.post("/api/1/cats").content(json).contentType(MediaType.APPLICATION_JSON_VALUE))
    	.andExpect(status().isBadRequest());
    }
    
    @Test
    public void getByName() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/1/cats/search?name=Tom").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getByDescription() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/1/cats/search?description=Bob cat").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void update() throws Exception {
    	Cat updatedCat = new Cat("Tommy", "Bob cat updated");
    	updatedCat.setId("123");
    	
    	when(service.update("123", updatedCat)).thenReturn(updatedCat);
    	
        String json = "{ \"name\": \"Tommy\", \"description\": \"Bob cat updated\" }";
        mvc.perform(MockMvcRequestBuilders.put("/api/1/cats/123").content(json).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }
    
    @Test
    public void updateFail() throws Exception {    	
    	when(service.update(eq("123"), any(Cat.class))).thenThrow(AnimalNotFoundException.class);

    	
    	String json = "{ \"name\": \"Tommy\", \"description\": \"Bob cat updated\" }";
    	mvc.perform(MockMvcRequestBuilders.put("/api/1/cats/123").content(json).contentType(MediaType.APPLICATION_JSON_VALUE))
    	.andExpect(status().isNotFound());
    }

    @Test
    public void delete() throws Exception {
    	cat.setId("123");
    	
    	doNothing().when(service).delete("123");
    	
        mvc.perform(MockMvcRequestBuilders.delete("/api/1/cats/123").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
    
    
}