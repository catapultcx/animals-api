package cx.catapult.animals.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

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
    
    @Test
    public void handleFormPost() throws Exception {
        String name = "Tom";
        String description = "Bob cat";
        String url = "/api/1/cats/form";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("name", name);
        map.add("description", description);

        when(service.create(any(Cat.class))).thenReturn(cat);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(url)
                .headers(headers)
                .params(map))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost:3000/cats"))
                .andReturn();

        assertEquals(HttpStatus.SEE_OTHER.value(), result.getResponse().getStatus());
    }

    
    @Test
    public void handleFormPutAndDelete() throws Exception {
        String id = "123";
        String name = "Tommy";
        String description = "Bob cat updated";
        String method = "PUT";
        String url = "/api/1/cats/form/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("_method", method);
        map.add("name", name);
        map.add("description", description);

        Cat updatedCat = new Cat(name, description);
        updatedCat.setId(id);

        when(service.update(eq(id), any(Cat.class))).thenReturn(updatedCat);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(url)
                .headers(headers)
                .params(map))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost:3000/cats"))
                .andReturn();

        assertEquals(HttpStatus.SEE_OTHER.value(), result.getResponse().getStatus());
    }
    
    @Test
    public void handleFormDelete() throws Exception {
        String catId = "123";
        String method = "DELETE";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("_method", method);

        doNothing().when(service).delete(catId);

        mvc.perform(MockMvcRequestBuilders.post("/api/1/cats/form/" + catId)
                .params(map)
                .headers(headers)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isSeeOther());

        verify(service).delete(catId);
    }


}