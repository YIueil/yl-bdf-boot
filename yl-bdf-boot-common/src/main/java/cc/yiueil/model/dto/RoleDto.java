package cc.yiueil.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * RoleDto 角色数据传输类
 *
 * @author 弋孓 YIueil@163.com
 * @version 1.0
 * @date 2023/9/10 1:55
 */
@Getter
@Setter
@ToString
@Schema(name = "角色数据传输对象")
public class RoleDto {
    private Long id;
    private String guid;
    @Schema(name = "名称")
    private String name;
    @Schema(name = "描述")
    private String description;
    @Schema(name = "已关联到功能")
    private Boolean hasFunction;
    @Schema(name = "已关联到权限")
    private Boolean hasPermission;
    private Long parentId;
}
