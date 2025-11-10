package cc.yiueil.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * ApplicationDto
 *
 * @author 弋孓 YIueil@163.com
 * @version 1.0
 * @date 2023/9/10 3:50
 * @see cc.yiueil.model.entity.ApplicationEntity
 */
@Getter
@Setter
@ToString
@Schema(name = "应用数据传输对象")
public class ApplicationDto {
    private Long id;
    private String guid;
    @Schema(name = "名称")
    private String name;
    @Schema(name = "描述")
    private String description;
    @Schema(name = "访问地址")
    private String url;
    @Schema(name = "图标地址")
    private String iconUrl;
    @Schema(name = "运行状态")
    private String status;
}
