package cx.catapult.animals.web;

import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.service.CatsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CatsControllerMyWayIT {

    @LocalServerPort
    private int port;

    private URL base;

    private Cat cat = new Cat("Tom", "Bob cat");

    @MockBean
    private CatsService catCervice;

    @Autowired
    private TestRestTemplate template;

    @BeforeEach
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/api/1/cats");
    }


    @Test
    void givenCats_whenGetAllCats_thenReturnsCats() {
        //given
        List<Cat> filteredCats = Arrays.asList(new Cat("name1","desc1"),new Cat("name2","desc2"));
        given(catCervice.all()).willReturn(filteredCats);

        //when
        ResponseEntity<Cat[]> response = template.getForEntity(base.toString(), Cat[].class);

        //then
        assertThat(response.getBody()).isNotEmpty();
        assertThat(response.getBody()).hasSize(2);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void givenCats_whenGetAllCatsByFilter_thenReturnsFilteredCats() {
        //given
        List<Cat> filteredCats = Arrays.asList(new Cat("name1","desc1"),new Cat("name2","desc2"));
        given(catCervice.getByFilter(any(),any())).willReturn(filteredCats);

        //when
        ResponseEntity<Cat[]> response = template.getForEntity(base.toString()+"?name=a&description=b", Cat[].class);

        //then
        assertThat(response.getBody()).isNotEmpty();
        assertThat(response.getBody()).hasSize(2);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void givenId_whenDeleteCat_thenReturnsTrue() {
        //given
        given(catCervice.delete(any())).willReturn(true);

        //when
        ResponseEntity<Boolean> response = template.exchange(base.toString() + "/123", HttpMethod.DELETE, HttpEntity.EMPTY, Boolean.class);

        //then
        assertThat(response.getBody()).isTrue();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }



    @Test
    void givenCat_whenUpdateCat_thenReturnsUpdatedCat() {
        //given
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Cat cat = new Cat("1", "name1","desc1");
        given(catCervice.update(any())).willReturn(cat);

        //when
        ResponseEntity<Cat> response = template.exchange(base.toString(), HttpMethod.PUT, new HttpEntity<>(cat, headers), Cat.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void givenCatWithNoId_whenUpdateCat_thenReturnsCat() {
        //given
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Cat cat = new Cat( "name1","desc1");

        //when
        ResponseEntity<Cat> response = template.exchange(base.toString(), HttpMethod.PUT, new HttpEntity<>(cat, headers), Cat.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

}
