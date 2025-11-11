package cc.yiueil.configuration;

import cc.yiueil.properties.HelloProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(HelloProperties.class)
public class HelloAutoConfiguration {
    @Bean
    public String hello(HelloProperties helloProperties) {
        return String.format("%s %s!", helloProperties.getPrefix(), helloProperties.getSuffix());
    }  
}