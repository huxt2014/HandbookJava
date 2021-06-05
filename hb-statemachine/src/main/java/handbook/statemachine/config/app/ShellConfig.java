package handbook.statemachine.config.app;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;

@Configuration
@Profile("shell")
@ComponentScan(basePackages = "handbook.statemachine.app.shell")
public class ShellConfig {

    @PostConstruct
    void test(){
        System.out.println("load");
    }

}
