package cc.yiueil.config;

import cc.yiueil.api.ImageResource;
import cc.yiueil.api.impl.SmmsImageHostImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ImageHostConfig {
    @Bean
    public ImageResource imageResource() {
        return new SmmsImageHostImpl();
    }
}
