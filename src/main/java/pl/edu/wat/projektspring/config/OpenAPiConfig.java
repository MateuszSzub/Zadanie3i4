package pl.edu.wat.projektspring.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Sklep z narzedziami", version = "v1"))
public class OpenAPiConfig {
}