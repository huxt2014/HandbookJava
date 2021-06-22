package handbook.hibernate.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("shell")
@ComponentScan(basePackages = "handbook.hibernate.app.shell")
public class ConfigShell {
}
