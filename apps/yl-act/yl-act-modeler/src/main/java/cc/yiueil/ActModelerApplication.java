package cc.yiueil;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class ActModelerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActModelerApplication.class, args);
    }
}
