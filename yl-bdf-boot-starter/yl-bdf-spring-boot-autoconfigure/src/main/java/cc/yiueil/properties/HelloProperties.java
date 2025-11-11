package cc.yiueil.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "yiueil.hello")
public class HelloProperties {  
    private String prefix = "你好";  
  
    private String suffix = "再见";  
  
    public String getPrefix() {  
        return prefix;  
    }  
  
    public void setPrefix(String prefix) {  
        this.prefix = prefix;  
    }  
  
    public String getSuffix() {  
        return suffix;  
    }  
  
    public void setSuffix(String suffix) {  
        this.suffix = suffix;  
    }  
}