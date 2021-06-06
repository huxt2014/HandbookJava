package handbook.statemachine;

import handbook.statemachine.orderexample.stm.spring.OrderServiceSpring;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"handbook.statemachine.orderexample",
                               "handbook.statemachine.config" })
public class AppShowSpring {
    public static void main(String[] args) throws Exception {
        SpringApplication app = new SpringApplication(AppShowSpring.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        ApplicationContext context = app.run(args);

        OrderServiceSpring service = context.getBean(OrderServiceSpring.class);
        service.processTest();
    }

}