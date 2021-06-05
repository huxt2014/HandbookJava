package handbook.statemachine;

import handbook.statemachine.orderexample.service.OrderService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"handbook.statemachine.orderexample",
                               "handbook.statemachine.config" })
public class App {
    public static void main(String[] args) throws Exception {
        SpringApplication app = new SpringApplication(App.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        ApplicationContext context = app.run(args);

        OrderService service = context.getBean(OrderService.class);
        service.processTest();
    }

}