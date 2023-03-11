package cx.catapult.animals.service;


import cx.catapult.animals.domain.Cat;
import cx.catapult.animals.domain.SearchRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@MockitoSettings
public class CatSearchServiceTest {

    private List<Cat> testDate= Stream.of(new Cat("Tom", "Friend of Jerry"),
                                        new Cat("Jerry", "Not really a cat"),
                                        new Cat("Bili", "Furry cat"))
            .collect(Collectors.toList());
    @Mock
    CatsService catsService;

    @InjectMocks
    CatSearchService catSearchService;

    @BeforeEach
    void beforeEach(){
        when(catsService.all()).thenReturn(testDate);
    }
    @ParameterizedTest
    @NullSource
    @EmptySource
    public void shouldReturnResultsByName(String description){
        List<Cat> results = catSearchService.search(new SearchRequest("Tom", description));
        assertThat(results).hasSize(1);
        assertThat(results.get(0)).isEqualTo(testDate.get(0));
    }

    @ParameterizedTest
    @NullSource
    @EmptySource
    public void shouldReturnResultsByDescription(String name){
        List<Cat> results = catSearchService.search(new SearchRequest(name, "Furry cat"));
        assertThat(results).hasSize(1);
        assertThat(results.get(0)).isEqualTo(testDate.get(2));
    }

    @Test
    public void shouldReturnResultsByNameAndDescription(){
        List<Cat> results = catSearchService.search(new SearchRequest("Bili", "Furry cat"));
        assertThat(results).hasSize(1);
        assertThat(results.get(0)).isEqualTo(testDate.get(2));
    }

    @Test
    public void shouldReturnNoResultsByName(){
        List<Cat> results = catSearchService.search(new SearchRequest("Citizen Khan", ""));
        assertThat(results).hasSize(0);
    }

    @Test
    public void shouldReturnNoResultsByDescription(){
        List<Cat> results = catSearchService.search(new SearchRequest(null, "Fubar"));
        assertThat(results).hasSize(0);
    }

    @Test
    public void shouldReturnAllResultsForEmptySearch(){
        List<Cat> results = catSearchService.search(new SearchRequest(null, null));
        assertThat(results).hasSize(3);
    }

    @Test
    public void shouldReturnNoResultsForNullSearchRequest(){
        reset(catsService);
        List<Cat> results = catSearchService.search(null);
        assertThat(results).hasSize(0);
    }

}
