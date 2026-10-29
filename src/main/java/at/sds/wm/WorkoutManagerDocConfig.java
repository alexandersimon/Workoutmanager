package at.sds.wm;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WorkoutManagerDocConfig {
    @Bean
    public OpenAPI openAPI () {
            return new OpenAPI ()
                    .info(new io.swagger.v3.oas.models.info.Info()
                    .title("Documentation for API Workout")
                    .version("1.0.0")
                    .description("Documentation...")
                    .license(new License().name("").url(""))
                    .contact(new Contact().name("").email("").url("")));
//                    .externalDocs (new ExternalDocumentation().description("").url("")));

    }
}