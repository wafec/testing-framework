package robtest.stateinterfw.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:beans.xml")
public class StateInterWebApplication {
    public static void run() {
        SpringApplication.run(StateInterWebApplication.class);
    }
}
