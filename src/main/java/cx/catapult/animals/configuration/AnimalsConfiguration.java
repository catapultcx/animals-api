package cx.catapult.animals.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AnimalsConfiguration {
    @Bean
    public ModelMapper getModelMapper(){
        return new ModelMapper();
    }
}
