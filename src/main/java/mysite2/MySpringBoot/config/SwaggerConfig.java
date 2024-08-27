package mysite2.MySpringBoot.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "MyFirstSpringBootApp Api",
                description = "System", version = "1.0.0",
                contact = @Contact(
                        name = "Endaltsev Anton",
                        email = "booba-anton@yandex.ru",
                        url = "http://http://80.87.107.222:8081/mysite2/people"
                )
        )
)
public class SwaggerConfig {

}
