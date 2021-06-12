package handbook.statemachine;

import handbook.statemachine.orderexample.stm.cola.OrderServiceCola;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * cola基本不可用用于生产环境，仅仅提供了一个stateless的实现思路，接口设计不错。
 * spring stm功能比较全面，可以作为实践的参照。
 */
@SpringBootApplication
@ComponentScan(basePackages = {"handbook.statemachine.orderexample",
                               "handbook.statemachine.config" })
public class AppShowCola {
    public static void main(String[] args) throws Exception {
        SpringApplication app = new SpringApplication(AppShowCola.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        ApplicationContext context = app.run(args);

        OrderServiceCola service = context.getBean(OrderServiceCola.class);
        service.processTest();
    }

}