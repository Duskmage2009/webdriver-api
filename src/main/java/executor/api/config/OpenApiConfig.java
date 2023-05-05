package executor.api.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI applicationAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Executor-Service API")
                        .description("API endpoints for webdriver-qacluster executor service")
                        .version("v1"))
                .externalDocs(new ExternalDocumentation()
                        .description("Executor-Service github")
                        .url("https://github.com/GFLcourses3/group1-microservice2"))
                .servers(List.of(
                        new Server()
                                .url("https://gfl-course-group1.up.railway.app")
                                .description("api server")
                ));
    }

}
