package cx.catapult.animals.web;

import cx.catapult.animals.domain.Arachnid;
import org.springframework.boot.test.context.SpringBootTest;

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
}
