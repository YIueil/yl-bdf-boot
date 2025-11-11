package cc.yiueil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
@SpringBootApplication
public class BootApplication {
    @Autowired
    String hello;

    @GetMapping(value = "/test")
    public String test(@RequestParam(required = false) Integer num) {
        System.out.println(hello);
        return hello;
    }

    public static void main(String[] args) {
        SpringApplication.run(BootApplication.class, args);
    }
}
