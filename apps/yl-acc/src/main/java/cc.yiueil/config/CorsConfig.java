package cc.yiueil.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        // 1. 创建 CORS 配置对象
        CorsConfiguration config = new org.springframework.web.cors.CorsConfiguration();
        
        // 2. 设置允许的源（前端地址）适用于开发环境
        config.addAllowedOriginPattern("*");
        
        // 3. 允许发送 Cookie
        config.setAllowCredentials(true);
        
        // 4. 允许的请求头
        config.addAllowedHeader("*");
        
        // 5. 允许的请求方法
        config.addAllowedMethod("*");
        
        // 6. 预检请求的缓存时间（秒）
        config.setMaxAge(3600L);
        
        // 7. 创建配置源并注册路径映射
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        
        return new CorsFilter(source);
    }
}
