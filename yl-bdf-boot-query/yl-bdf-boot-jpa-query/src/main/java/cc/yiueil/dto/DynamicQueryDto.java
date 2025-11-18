package cc.yiueil.dto;

import cc.yiueil.query.DynamicQueryConfig;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * DynamicQueryDto 动态查询传输类
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/31 23:27
 * @version 1.0
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@Schema(name = "动态查询参数")
public class DynamicQueryDto {
    @NotNull
    @Schema(name = "查询服务配置的id")
    private String configId;

    @Schema(name = "查询配置服务的文件名")
    private String configFile;

    @Schema(name = "查询配置服务所在的路径")
    private String configPath;

    @Schema(name = "查询的过滤条件")
    private Map<String, Object> filter;

    @Schema(name = "查询配置")
    private DynamicQueryConfig config;

    public DynamicQueryDto(String configPath, String configFile, String configId) {
        this.configId = configId;
        this.configPath = configPath;
        this.configFile = configFile;
    }

    /**
     * 获取文件类路径
     * @return 文件类路径
     */
    public String getFullPath() {
        return getConfigPath() + '/' + getConfigFile();
    }
}
