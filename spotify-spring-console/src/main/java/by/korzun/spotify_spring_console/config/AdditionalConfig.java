package by.korzun.spotify_spring_console.config;

import by.korzun.spotify_spring_console.domain.TrackHistory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AdditionalConfig {
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .registerModule(new JavaTimeModule());
    }

    @Bean
    @SneakyThrows
    public List<TrackHistory> allTrackHistory() {
        return objectMapper().readValue(
                ClassLoader.getSystemClassLoader().getResourceAsStream("init.json"),
                new TypeReference<List<TrackHistory>>(){});
    }
}
