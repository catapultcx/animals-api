package cx.catapult.animals.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.util.TestUtil;

@SpringBootTest
@AutoConfigureMockMvc
public class CatsControllerTest {

    @Autowired
    private MockMvc mvc;

    private Cat cat = new Cat("Tom", "Bob cat");

    @Test
    public void all() throws Exception {
	mvc.perform(MockMvcRequestBuilders.get("/api/1/cats").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
    }

    @Test
    public void get() throws Exception {
	MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/api/1/cats").content(TestUtil.javaToJson(cat))
		.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isCreated()).andReturn();
	Cat created = TestUtil.jsonToJava(result.getResponse().getContentAsString(), Cat.class);

	result = mvc.perform(
		MockMvcRequestBuilders.get("/api/1/cats/".concat(created.getId())).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk()).andReturn();
	Cat retrieved = TestUtil.jsonToJava(result.getResponse().getContentAsString(), Cat.class);
	assertThat(retrieved).isNotNull().isEqualTo(created);
    }

    @Test
    public void getThatDoesNotExist() throws Exception {
	mvc.perform(MockMvcRequestBuilders.get("/api/1/cats/NotExists").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound()).andReturn();
    }

    @Test
    public void create() throws Exception {
	MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/api/1/cats").content(TestUtil.javaToJson(cat))
		.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isCreated()).andReturn();
	Cat actual = TestUtil.jsonToJava(result.getResponse().getContentAsString(), Cat.class);
	assertThat(actual).isNotNull();
	assertThat(actual.getId()).isNotNull();
	assertThat(actual.getName()).isNotNull().isEqualTo(cat.getName());
	assertThat(actual.getDescription()).isNotNull().isEqualTo(cat.getDescription());
	assertThat(actual.getGroup()).isNotNull().isEqualTo(cat.getGroup());
    }

    @Test
    public void createWithBadRequest() throws Exception {
	mvc.perform(MockMvcRequestBuilders.post("/api/1/cats").content(TestUtil.javaToJson(Cat.builder().build()))
		.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isBadRequest());
    }

    @Test
    public void update() throws Exception {
	MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/api/1/cats").content(TestUtil.javaToJson(cat))
		.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isCreated()).andReturn();
	Cat toBeUpdated = TestUtil.jsonToJava(result.getResponse().getContentAsString(), Cat.class);
	toBeUpdated.setDescription("American Shorthair");
	mvc.perform(MockMvcRequestBuilders.put("/api/1/cats").content(TestUtil.javaToJson(toBeUpdated))
		.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isNoContent());
	result = mvc.perform(MockMvcRequestBuilders.get("/api/1/cats/".concat(toBeUpdated.getId()))
		.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
	assertThat(result.getResponse().getContentAsString()).isNotNull().isNotEmpty();
	Cat actual = TestUtil.jsonToJava(result.getResponse().getContentAsString(), Cat.class);
	assertThat(actual).isNotNull().isEqualTo(toBeUpdated);
    }

    @Test
    public void updateThatDoesNotExist() throws Exception {
	Cat entity = cat.toBuilder().id("NotExists").build();
	mvc.perform(MockMvcRequestBuilders.put("/api/1/cats").content(TestUtil.javaToJson(entity))
		.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isNotFound());

    }

    @Test
    public void updateWithBadRequest() throws Exception {
	MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/api/1/cats").content(TestUtil.javaToJson(cat))
		.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isCreated()).andReturn();
	Cat toBeUpdated = TestUtil.jsonToJava(result.getResponse().getContentAsString(), Cat.class);
	toBeUpdated.setDescription(null);
	mvc.perform(MockMvcRequestBuilders.put("/api/1/cats").content(TestUtil.javaToJson(toBeUpdated))
		.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isBadRequest());
    }

    @Test
    public void remove() throws Exception {
	MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/api/1/cats").content(TestUtil.javaToJson(cat))
		.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isCreated()).andReturn();
	Cat created = TestUtil.jsonToJava(result.getResponse().getContentAsString(), Cat.class);
	mvc.perform(MockMvcRequestBuilders.delete("/api/1/cats/{id}", created.getId()))
		.andExpect(status().isNoContent());
    }

    @Test
    public void removeThatDoesNotExist() throws Exception {
	mvc.perform(MockMvcRequestBuilders.delete("/api/1/cats/{id}", "NotExists")).andExpect(status().isNotFound());
    }

}