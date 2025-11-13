package cc.yiueil.configuration;

import cc.yiueil.util.ext.SpringContextUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreConfiguration {

    @Bean
    public SpringContextUtils springContextUtils() {
        return new SpringContextUtils();
    }
}
