package cx.catapult.animals.web;


import cx.catapult.animals.domain.Cat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URL;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class CatsControllerIT extends BaseControllerIT<Cat> {

    @Autowired
    private TestRestTemplate template;

    @Override
    protected Cat createInstance() {
        return createInstance("Tom", "Bob cat");
    }

    @Override
    protected Cat createInstance(final String name,
                                 final String description) {
        return new Cat(name, description);
    }

    @Override
    protected String getUrlSuffix() {
        return "cats";
    }

    @Override
    protected int getExpectedItems() {
        return 7;
    }
}
