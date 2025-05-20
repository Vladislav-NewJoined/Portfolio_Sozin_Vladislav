package task14_2_1.zadanye3;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    static String getBean() {
        return "some string";
    }
}
