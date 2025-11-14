package cc.yiueil.properties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "bdf.storage")
public class FileResourceProperties {
    
    /**
     * 文件上传路径
     */
    private String uploadPath;
    
    /**
     * SM.MS图床配置
     */
    private SmmsConfig smms = new SmmsConfig();
    
    @Data
    public static class SmmsConfig {
        private String apiKey;
    }
}