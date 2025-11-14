package cc.yiueil.properties;

import cc.yiueil.dto.UserDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "bdf.security")
public class SecurityProperties {
    private String type = "jwt";

    private UserDto visitor;
}
