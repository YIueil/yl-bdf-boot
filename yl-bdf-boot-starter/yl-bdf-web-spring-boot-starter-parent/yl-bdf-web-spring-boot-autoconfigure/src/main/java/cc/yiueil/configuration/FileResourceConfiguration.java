package cc.yiueil.configuration;

import cc.yiueil.controller.ResourceController;
import cc.yiueil.properties.FileResourceProperties;
import cc.yiueil.service.ResourceService;
import cc.yiueil.service.impl.ResourceServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = FileResourceProperties.class)
public class FileResourceConfiguration {

    @Bean
    @ConditionalOnMissingBean(ResourceController.class)
    public ResourceController resourceController() {
        return new ResourceController();
    }

    @Bean
    @ConditionalOnMissingBean(ResourceService.class)
    public ResourceService resourceService() {
        return new ResourceServiceImpl();
    }
}
