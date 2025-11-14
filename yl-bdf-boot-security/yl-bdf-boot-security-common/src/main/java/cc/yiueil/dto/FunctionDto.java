package cc.yiueil.dto;

import cc.yiueil.entity.FunctionEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * FunctionDto 应用功能数据传输对象
 *
 * @author 弋孓 YIueil@163.com
 * @version 1.0
 * @date 2023/9/10 4:22
 * @see FunctionEntity
 */
@Getter
@Setter
@ToString
@Schema(name = "应用功能数据传输对象")
public class FunctionDto {
    private Long id;
    private String guid;
    @Schema(name = "名称")
    private String name;
    @Schema(name = "唯一标识名称")
    private String rightName;
    @Schema(name = "描述")
    private String description;
    @Schema(name = "资源地址")
    private String url;
    @Schema(name = "资源类型")
    private String type;
    @Schema(name = "方法")
    private String method;
    @Schema(name = "父节点id")
    private Long parentId;
    @Schema(name = "应用id")
    private Long applicationId;
}
