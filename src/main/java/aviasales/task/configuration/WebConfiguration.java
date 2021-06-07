package aviasales.task.configuration;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.ZoneId;
import java.util.TimeZone;

@Configuration
public class WebConfiguration {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer init() {
        return builder -> builder.timeZone(TimeZone.getTimeZone(ZoneId.of("UTC")));
    }
}