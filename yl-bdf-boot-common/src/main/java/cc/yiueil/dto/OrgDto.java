package cc.yiueil.dto;

import cc.yiueil.entity.OrgEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * OrgDto org数据传输类
 *
 * @author 弋孓 YIueil@163.com
 * @version 1.0
 * @date 2023/10/4 15:03
 * @see OrgEntity
 */
@Getter
@Setter
@ToString
@Schema(name = "机构数据传输对象")
public class OrgDto {
    private Long id;
    private String guid;
    @Schema(name = "机构名称")
    private String name;
    @Schema(name = "机构代码")
    private String code;
    @Schema(name = "类型(单位、部门)")
    private String type;
    @Schema(name = "描述")
    private String description;
    @Schema(name = "父节点id")
    private Long parentId;
}
