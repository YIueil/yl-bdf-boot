package cc.yiueil.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * PermissionDto 权限数据传输类
 *
 * @author 弋孓 YIueil@163.com
 * @version 1.0
 * @date 2023/9/10 1:53
 */
@Getter
@Setter
@ToString
@Schema(name = "权限数据传输类")
public class PermissionDto {
    private Long id;
    private String guid;
    @Schema(name = "名称")
    private String name;
    @Schema(name = "唯一标识名称")
    private String rightName;
    @Schema(name = "描述")
    private String description;
    private Long parentId;
}
