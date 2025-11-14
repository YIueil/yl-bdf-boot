package cc.yiueil2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * BootApplication 如果包名不是 cc.yiueil 需要这边额外的配置 @EntityScan 和 @EnableJpaRepositories 指定要扫描的包
 *
 * @author 弋孓 YIueil@163.com
 * @version 1.0
 * @date 2025/11/14 15:57
 */
@EnableScheduling
@EntityScan(basePackages = {"cc.yiueil.entity", "cc.yiueil2.entity"})
@EnableJpaRepositories(basePackages = {"cc.yiueil.repository", "cc.yiueil2.repository"})
@SpringBootApplication
public class BootApplication {
    public static void main(String[] args) {
        SpringApplication.run(BootApplication.class, args);
    }
}