# yl-bdf-boot
yl-bdf project for Spring Boot

## application
在开发中的应用父模块

## common
公共模块

## jpa
jpa集成模块

## product
产品功能模块

## security
SpringSecurity安全集成模块

## start
启动测试模块

## swagger
swagger集成模块，使用的是springdoc，基于1.8.0版本

## 使用方法
### 1 Pom 添加 Starter
```xml
        <dependency>
            <groupId>cc.yiueil</groupId>
            <artifactId>yl-bdf-spring-boot-starter</artifactId>
            <version>0.0.1-SNAPSHOT</version>
        </dependency>
```

### 2 如果包名称不是 cc.yiueil 开头
启动类上添加`@EntityScan`和`@EnableJpaRepositories`, 指定具体的实体扫描路径。
```java
// 重点是这两个注解
@EntityScan(basePackages = {"cc.yiueil.entity", "cc.yiueil2.entity"})
@EnableJpaRepositories(basePackages = {"cc.yiueil.repository", "cc.yiueil2.repository"})

@EnableScheduling
@SpringBootApplication
public class BootApplication {
    public static void main(String[] args) {
        SpringApplication.run(BootApplication.class, args);
    }
}
```