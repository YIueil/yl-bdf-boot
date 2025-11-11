package cc.yiueil.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "动态查询配置")
public class DynamicQueryConfig {
    @ApiModelProperty(value = "结果集转大写")
    private Boolean toUpperCase = false;
}
