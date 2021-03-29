package cx.catapult.animals.web;

import cx.catapult.animals.domain.Crustacean;
import cx.catapult.animals.service.CrustaceansService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/1/crustaceans", produces = MediaType.APPLICATION_JSON_VALUE)
public class CrustaceansController extends BaseController<Crustacean> {
    public CrustaceansController(final CrustaceansService service) {
        super(service);
    }
}
