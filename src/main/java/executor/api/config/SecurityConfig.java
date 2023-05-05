package executor.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity
@Configuration
public class SecurityConfig implements WebMvcConfigurer {

    private static final String SCENARIO_PATH = "/scenario/**";
    private static final String PROXY_PATH = "/proxy/**";
    private static final String STATIC_LOCATIONS = "classpath:/static/";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(SCENARIO_PATH, PROXY_PATH)
                .addResourceLocations(STATIC_LOCATIONS);
    }
}
