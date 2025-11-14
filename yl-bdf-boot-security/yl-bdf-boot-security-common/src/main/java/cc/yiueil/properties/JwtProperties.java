package cc.yiueil.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "bdf.jwt")
public class JwtProperties {
    /**
     * 超时时间
     */
    private Long expireSeconds = 1800L;

    /**
     * 公用 Token
     */
    private String publicToken = "public-token";


    /**
     * 前端jwt请求头自定义名称
     */
    private String headerParamsName = "yl-token";

    /**
     * jwt 生成私钥
     */
    private String privateKey = "YIueil";

    /**
     * 拦截的路径模式, 例如 /**, /api/**
     */
    private String[] includePathPatterns = {"/**"};

    /**
     * 排除的路径模式, 例如 /login, /static/**
     */
    private String[] excludePathPatterns = {};
}
