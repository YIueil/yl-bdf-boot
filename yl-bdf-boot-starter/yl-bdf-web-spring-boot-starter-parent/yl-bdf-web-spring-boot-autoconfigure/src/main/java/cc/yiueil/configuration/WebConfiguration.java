package cc.yiueil.configuration;

import cc.yiueil.advise.LogAdvise;
import cc.yiueil.advise.TimeIntervalAdvise;
import cc.yiueil.controller.ResourceController;
import cc.yiueil.controller.VersionController;
import cc.yiueil.handler.CustomExceptionHandler;
import cc.yiueil.service.ResourceService;
import cc.yiueil.service.impl.ResourceServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * WebAutoConfiguration Web模块的自动配置
 *
 * @author 弋孓 YIueil@163.com
 * @version 1.0
 * @date 2025/11/12 11:41
 */
@Configuration
public class WebConfiguration {

    @Bean
    // 关键点: 只有当容器中没有 CustomExceptionHandler 类型的 Bean 时, 才创建这个 Bean, 允许用户自定义他。
    @ConditionalOnMissingBean(CustomExceptionHandler.class)
    public CustomExceptionHandler customExceptionHandler() {
        return new CustomExceptionHandler();
    }

    @Bean
    public LogAdvise logAdvise() {
        return new LogAdvise();
    }

    @Bean
    public TimeIntervalAdvise timeIntervalAdvise() {
        return new TimeIntervalAdvise();
    }

    @Bean
    public VersionController versionController() {
        return new VersionController();
    }

    @Bean
    public ResourceController resourceController() {
        return new ResourceController();
    }

    @Bean
    public ResourceService resourceService() {
        return new ResourceServiceImpl();
    }
}
