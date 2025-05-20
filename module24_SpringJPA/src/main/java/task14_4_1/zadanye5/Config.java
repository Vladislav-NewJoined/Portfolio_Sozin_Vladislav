package task14_4_1.zadanye5;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    static String getBean() {
        return "some string";
    }
}
