package cc.yiueil.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * DynamicQueryConfig 动态查询配置
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/31 23:29
 * @version 1.0
 */
@Getter
@Setter
@Schema(name = "动态查询配置")
public class DynamicQueryConfig {
    @Schema(name = "结果集转大写")
    private Boolean toUpperCase = false;
}
