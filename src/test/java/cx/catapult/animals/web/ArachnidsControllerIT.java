package cx.catapult.animals.web;

import static org.assertj.core.api.Assertions.assertThat;

import cx.catapult.animals.domain.Arachnid;
import java.net.MalformedURLException;
import java.net.URL;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class ArachnidsControllerIT extends BaseControllerIT<Arachnid> {
    @Override
    protected Arachnid createInstance() {
        return createInstance("Spider McSpiderface", "Hairy");
    }

    @Override
    protected Arachnid createInstance(final String name,
                                      final String description) {
        return new Arachnid(name, description);
    }

    @Override
    protected String getUrlSuffix() {
        return "arachnids";
    }

    // This is a bit nasty but is a side effect of having hardcoded lists in the service
    @Override
    protected int getExpectedItems() {
        return 5;
    }

    @Test
    public void deleteShouldWork() {
        final String testString = "Test 1";
        final Arachnid created = create(testString);
        final ResponseEntity<String> response = template.getForEntity(getUrl(created.getId()), String.class);
        assertThat(response.getBody()).isNotEmpty();
        assertThat(response.getBody()).contains(testString);
    }

    @Test
    public void updateShouldWork() throws MalformedURLException {
        Arachnid instance = createInstance();
        Arachnid updatedInstance = createInstance("Updated name", "Updated desc");
        ResponseEntity<Arachnid> postResponse = (ResponseEntity<Arachnid>) template.postForEntity(getUrl(), instance,
        instance.getClass());
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        final String id = postResponse.getBody().getId();
        final String url = getUrl(id);
        template.put(url, updatedInstance);

        ResponseEntity<Arachnid> response = template.getForEntity(url, Arachnid.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getId()).isEqualTo(id);
        assertThat(response.getBody().getName()).isEqualTo(updatedInstance.getName());
        assertThat(response.getBody().getDescription()).isEqualTo(updatedInstance.getDescription());
    }
}
