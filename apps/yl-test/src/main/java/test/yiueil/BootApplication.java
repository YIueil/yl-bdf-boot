package test.yiueil;

import cc.yiueil.util.ext.SpringContextUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BootApplication {
    public static void main(String[] args) {
        SpringApplication.run(BootApplication.class, args);
        SpringContextUtils bean = SpringContextUtils.getBean(SpringContextUtils.class);
        System.out.println(bean);
    }
}
