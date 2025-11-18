package cc.yiueil.configuration;

import cc.yiueil.properties.JwtProperties;
import cc.yiueil.properties.SecurityProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityConfiguration {
    @Bean
    @ConditionalOnMissingBean(JwtProperties.class)
    public JwtProperties jwtProperties() {
        return new JwtProperties();
    }
}
