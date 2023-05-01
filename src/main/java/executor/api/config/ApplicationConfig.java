package executor.api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import executor.api.model.ProxyResponseDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;

@Configuration
@EnableScheduling
public class ApplicationConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public ParameterizedTypeReference<List<ProxyResponseDTO>> parameterizedTypeReferenceListProxy() {
        return new ParameterizedTypeReference<>() {};
    }

}