package cc.yiueil.configuration;

import cc.yiueil.controller.OrupController;
import cc.yiueil.data.impl.JpaBaseDao;
import cc.yiueil.interceptor.AuthenticateInterceptor;
import cc.yiueil.properties.JwtProperties;
import cc.yiueil.properties.SecurityProperties;
import cc.yiueil.repository.OrupBaseDao;
import cc.yiueil.service.MessageService;
import cc.yiueil.service.OrupService;
import cc.yiueil.service.impl.MessageServiceImpl;
import cc.yiueil.service.impl.OrupServiceImpl;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
@ConditionalOnProperty(value = "bdf.security.type", havingValue = "jwt")
public class JwtConfiguration {


    //region 拦截器注册
    @Bean
    @ConditionalOnMissingBean(AuthenticateInterceptor.class)
    public AuthenticateInterceptor authenticateInterceptor(OrupService orupService, JwtProperties jwtProperties) {
        // 这里可以根据properties创建一个定制化的拦截器实例
        return new AuthenticateInterceptor(orupService, jwtProperties);
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer(AuthenticateInterceptor authenticateInterceptor, JwtProperties jwtProperties) {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(@NotNull InterceptorRegistry registry) {
                InterceptorRegistration interceptorRegistration = registry.addInterceptor(authenticateInterceptor);
                interceptorRegistration.addPathPatterns(jwtProperties.getIncludePathPatterns());
                interceptorRegistration.excludePathPatterns(jwtProperties.getExcludePathPatterns());
            }
        };
    }
    //endregion

    @Bean
    @ConditionalOnBean(JpaBaseDao.class)
    @ConditionalOnMissingBean(OrupBaseDao.class)
    public OrupBaseDao orupBaseDao() {
        return new OrupBaseDao();
    }

    @Bean
    @ConditionalOnMissingBean(OrupController.class)
    public OrupController orupController(OrupService orupService, JwtProperties jwtProperties, SecurityProperties securityProperties) {
        return new OrupController(orupService, jwtProperties, securityProperties);
    }

    @Bean
    @ConditionalOnMissingBean(OrupService.class)
    public OrupService orupService() {
        return new OrupServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(MessageService.class)
    public MessageService messageService() {
        return new MessageServiceImpl();
    }
}
