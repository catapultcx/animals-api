package cx.catapult.animals.web;


import static org.assertj.core.api.Assertions.assertThat;

import java.net.URL;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class AnimalsControllerIT {
    @LocalServerPort
    private int port;

    private URL base;

    @Autowired
    private TestRestTemplate template;

    @BeforeEach
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/api/1/animals/results?keyword=Tom");
    }

    @Test
    public void resultsShouldWork() throws Exception {

        List items = template.getForObject(base.toString(), List.class);
        assertThat(items.size()).isGreaterThanOrEqualTo(0);
    }

}
