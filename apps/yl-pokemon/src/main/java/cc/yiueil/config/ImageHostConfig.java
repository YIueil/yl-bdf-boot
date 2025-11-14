package cc.yiueil.config;

import cc.yiueil.service.ImageResource;
import cc.yiueil.service.impl.SmmsImageBedImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ImageHostConfig {
    @Bean
    public ImageResource imageResource() {
        return new SmmsImageBedImpl();
    }
}
