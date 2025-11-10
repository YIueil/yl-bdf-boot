package cc.yiueil.model.vo;

import cc.yiueil.model.dto.PermissionDto;
import cc.yiueil.model.dto.RoleDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * UserVo 用户视图对象
 *
 * @author 弋孓 YIueil@163.com
 * @version 1.0
 * @date 2023/9/10 1:40
 * @see cc.yiueil.model.entity.UserEntity
 * @see cc.yiueil.model.dto.UserDto
 */
@Getter
@Setter
@ToString
@Schema(name = "用户视图对象")
public class UserVo {
    @Schema(name = "id")
    private Long id;
    @Schema(name = "guid")
    private String guid;
    @Schema(name = "用户名")
    private String userName;
    @Schema(name = "登陆名")
    private String loginName;
    @Schema(name = "联系电话")
    private String phoneNumber;
    @Schema(name = "邮箱")
    private String email;
    @Schema(name = "头像地址")
    private String avatarUrl;
    @Schema(name = "个性签名")
    private String signature;
    @Schema(name = "扩展1")
    private String extendProperty1;
    @Schema(name = "扩展2")
    private String extendProperty2;
    @Schema(name = "扩展3")
    private String extendProperty3;
    @Schema(name = "扩展4")
    private String extendProperty4;
    @Schema(name = "角色集合")
    private List<RoleDto> roleDtoList;
    @Schema(name = "权限集合")
    private List<PermissionDto> permissionDtoList;
}
