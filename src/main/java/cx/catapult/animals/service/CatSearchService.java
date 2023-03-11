package cx.catapult.animals.service;

import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.domain.SearchRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class CatSearchService {

    @Autowired
    private CatsService service;

    public List<Cat> search(SearchRequest searchRequest){
        if(searchRequest == null){
            return Collections.EMPTY_LIST;
        }
        Function<String, Boolean> nameSearch = (String name)->{
            if(!StringUtils.isEmpty(searchRequest.getName())){
                return searchRequest.getName().equalsIgnoreCase(name);
            }
            return false;
        };

        Function<String, Boolean> descSearch = (String description)->{
            if(!StringUtils.isEmpty(searchRequest.getDescription())){
                return searchRequest.getDescription().equalsIgnoreCase(description);
            }
            return false;
        };

        return service.all().stream().filter(c -> !StringUtils.isEmpty(searchRequest.getName())
            && !StringUtils.isEmpty(searchRequest.getDescription()) ?
                (nameSearch.apply(c.getName())
                        && descSearch.apply(c.getDescription())) :
                (nameSearch.apply(c.getName())
                        || descSearch.apply(c.getDescription())))
                .collect(Collectors.toList());
    }
}
