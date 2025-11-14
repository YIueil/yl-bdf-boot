package cc.yiueil.configuration;

import cc.yiueil.util.ext.SpringContextUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreConfiguration {

    @Bean
    @ConditionalOnMissingBean(value = SpringContextUtils.class)
    public SpringContextUtils springContextUtils() {
        return new SpringContextUtils();
    }
}
